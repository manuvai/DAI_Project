package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import models.Article;
import models.Composer;
import models.Panier;
import models.Utilisateur;
import models.keys.ComposerKey;
import repositories.ArticleRepository;
import repositories.CreneauRepository;
import repositories.PanierRepository;
import repositories.UtilisateurRepository;
import servlets.mailing.EmailSender;
import servlets.mailing.notifications.CommandeCreeNotification;

/**
 * Servlet implementation class PayerServlet
 */
@WebServlet("/PayerServlet")
public class PayerServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	PanierRepository pr = new PanierRepository();
	CreneauRepository cr = new CreneauRepository();
	UtilisateurRepository ur = new UtilisateurRepository();
	ArticleRepository ar = new ArticleRepository();

	EmailSender emailSender = new EmailSender(getProperties());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// recuperation des différentes infos
		final HttpSession session = request.getSession();
		final Utilisateur user = (Utilisateur) session.getAttribute("user");
		final int ptconso = (int) session.getAttribute("ptfidelConso");
		final Panier p = (Panier) session.getAttribute("panierAValider");
		final Map<Article, Composer> composition = p.getComposers();

		final List<Integer> produitsId = (List<Integer>) request.getSession().getAttribute("articleRempla");

		for (final Integer articleId : produitsId) {
			final int qteAdd = Integer.parseInt(request.getParameter("" + articleId));
			if (qteAdd != 0) {
				final Article article = ar.findById(articleId);
				if (composition.containsKey(article)) {
					composition.get(article).setQte(qteAdd + composition.get(article).getQte());
				} else {
					final Composer c = new Composer();
					c.setQte(qteAdd);
					final ComposerKey composerKey = new ComposerKey(articleId, p.getId());
					c.setKey(composerKey);
					p.getComposers().put(ar.findById(articleId), c);
				}
			}

		}

		double montantFinal = 0;

		for (final Map.Entry<Article, Composer> entry : composition.entrySet()) {
			final Article article = entry.getKey();
			final Composer composer = entry.getValue();
			final int quantity = composer.getQte();
			montantFinal += quantity * article.getPrixUnitaire() * (1 + article.getPromotion() / 100);
		}

		p.setComposers(composition);

		session.setAttribute("apayer", montantFinal);

		final int ajoutFidelite = (int) (montantFinal / 5);
		user.setPtFidelite(user.getPtFidelite() - ptconso * 10 + ajoutFidelite);
		ur.update(user);

		pr.update(p);

		try {
			sendNotification(p);
		} catch (final MessagingException e) {
			e.printStackTrace();
		}

		final RequestDispatcher rd = request.getRequestDispatcher("panierEnregistrer");
		rd.forward(request, response);
	}

	/**
	 * Gestion de l'envoi de la notification à l'utilisateur.
	 *
	 * @param p
	 * @throws MessagingException
	 * @throws AddressException
	 */
	private void sendNotification(final Panier p) throws AddressException, MessagingException {
		if (p == null) {
			return;
		}

		final Session session = pr.getSession();
		session.beginTransaction();

		final Panier panier = pr.findById(p.getId(), session);

		final CommandeCreeNotification notification = new CommandeCreeNotification(panier.getUtilisateur(),
				panier.getCreneau(), panier);
		emailSender.send(p.getUtilisateur().getEmail(), notification);

		session.close();
	}

}
