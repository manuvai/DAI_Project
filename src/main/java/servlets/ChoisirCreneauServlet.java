package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Creneau;
import models.Utilisateur;
import repositories.CreneauRepository;
import repositories.MagasinRepository;
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class ChoisirCreneauServlet
 */
@WebServlet("/ChoisirCreneauServlet")
public class ChoisirCreneauServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CreneauRepository cr = new CreneauRepository();
	UtilisateurRepository ur = new UtilisateurRepository();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChoisirCreneauServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String magasin = request.getParameter("magasin");

		List<Creneau> cx = cr.findCreneauByMagasin(Integer.parseInt(magasin));
		HttpSession session = request.getSession();
		session.setAttribute("creneaux", cx);
		session.setAttribute("magasinRetrait", magasin);
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		Double total = (Double) session.getAttribute("totalPanierValidation");
		String utiliserPoints = request.getParameter("utiliserPoints");
		boolean utiliserPointsBool = "true".equals(utiliserPoints);
		int reduc = 0;
		if (utiliserPointsBool) {

			if (user.getPtFidelite() / 10 < total) {
				reduc = user.getPtFidelite() / 10;

			} else {
				reduc = total.intValue();

			}
		}
		session.setAttribute("ptfidelConso", reduc);

		double montantAPayer = total - reduc;

		session.setAttribute("apayer", montantAPayer);

		RequestDispatcher rd = request.getRequestDispatcher("choixCreneau");
		rd.forward(request, response);
	}

}
