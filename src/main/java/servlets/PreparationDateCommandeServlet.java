package servlets;



import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import models.Panier;
import models.Panier.Etat;
import repositories.PanierRepository;
import servlets.mailing.EmailSender;
import servlets.mailing.notifications.CommandePreteNotification;

/**
 * Servlet implementation class PreparationDateCommandeServlet
 */
@WebServlet("/PreparationDateCommandeServlet")
public class PreparationDateCommandeServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	PanierRepository pr = new PanierRepository();
	EmailSender emailSender = new EmailSender(getProperties());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;

		final String idPanier = request.getParameter("idPanier");
		final String dateJs = request.getParameter("DateFin");

		final Panier panier = pr.findById(Integer.parseInt(idPanier));
		Date date;



		if (dateJs == null) {
			date = new Date(Long.parseLong(request.getParameter("DateDebut")));
			panier.setDateDebutPreparation(date);
		} else {
			date = new Date(Long.parseLong(dateJs));
			panier.setDateFinPreparation(date);
			panier.setEtat(Etat.PRETE);
		}

		pr.update(panier);

		if (Etat.PRETE.equals(panier.getEtat())) {
			try {
				sendNotification(panier);
			} catch (final MessagingException e) {
				e.printStackTrace();
			}
		}

		rd = request.getRequestDispatcher("preparationdetail");
		rd.forward(request,response);
	}

	/**
	 * Envoi de la notification à l'utilisateur.
	 *
	 * @param panier
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private void sendNotification(final Panier panier) throws AddressException, MessagingException {

		final Session session = pr.getSession();
		session.beginTransaction();
		final CommandePreteNotification notification = new CommandePreteNotification(panier.getUtilisateur(),
				panier.getCreneau(), panier);

		session.close();
		emailSender.send(panier.getUtilisateur().getEmail(), notification);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
