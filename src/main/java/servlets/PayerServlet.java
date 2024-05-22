package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Article;
import models.Creneau;
import models.Panier;
import models.Utilisateur;
import models.keys.ComposerKey;
import models.Composer;
import repositories.ArticleRepository;
import repositories.CreneauRepository;
import repositories.PanierRepository;
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class PayerServlet
 */
@WebServlet("/PayerServlet")
public class PayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PanierRepository pr = new PanierRepository();
	CreneauRepository cr = new CreneauRepository();
	UtilisateurRepository ur = new UtilisateurRepository();
	ArticleRepository ar = new ArticleRepository();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayerServlet() {
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

		// recuperation des différentes infos
		doGet(request, response);
		String creneauS = request.getParameter("creneau");
		HttpSession session = request.getSession();
		session.setAttribute("creneau", creneauS);
		String magasin = (String) session.getAttribute("magasinRetrait");
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		Creneau creneau = cr.findById(Integer.parseInt(creneauS));
		int ptconso = (int) session.getAttribute("ptfidelConso");

		Panier p = new Panier(user, creneau);
		pr.create(p);
		ArrayList<String> numeros = (ArrayList<String>) session.getAttribute("numeros");

		// ajouter element au panier
		for (String num : numeros) {
			Composer c = new Composer();
			c.setQte((int) session.getAttribute(num));
			ComposerKey composerKey = new ComposerKey(Integer.parseInt(num), p.getId());
			c.setKey(composerKey);

			p.getComposers().put(ar.findById(Integer.parseInt(num)), c);

			session.removeAttribute(num);
		}
		numeros.clear();
		session.setAttribute("numeros", numeros);
		session.setAttribute("nbrArticleTotal", 0);
		// ajout des pt de fidelites
		double totalPayer = (double) session.getAttribute("apayer");

		int ajoutFidelite = (int) (totalPayer / 5);
		user.setPtFidelite(user.getPtFidelite() - ptconso * 10 + ajoutFidelite);
		ur.update(user);
		// mettre a jour panier
		pr.update(p);
		RequestDispatcher rd = request.getRequestDispatcher("panierEnregistrer");
		rd.forward(request, response);
	}

}
