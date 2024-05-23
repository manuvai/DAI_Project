package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Article;
import models.Utilisateur;
import repositories.ArticleRepository;
import repositories.CategorieRepository;
import repositories.UtilisateurRepository;
import models.Article.Nutriscore;
import models.Categorie;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	ArticleRepository articleRepository = new ArticleRepository();
	UtilisateurRepository ur = new UtilisateurRepository();
	CategorieRepository cr = new CategorieRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final HttpSession session = request.getSession();

		final Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
		if (utilisateur == null) {
			final RequestDispatcher rd = request.getRequestDispatcher("connexion");
			rd.forward(request, response);
			return;
		}


		Long nbrTotal = ur.findNombreArticlesDiffCommandesUser(utilisateur.getId());
		Long nbrBioTotal = ur.findNombreArticlesBIODiffCommandesUser(utilisateur.getId());
		Long nbrA = ur.findNombreArticlesNutriscoreDiffCommandesUser(utilisateur.getId(), "A");
		Long nbrB = ur.findNombreArticlesNutriscoreDiffCommandesUser(utilisateur.getId(), "B");
		Long nbrC = ur.findNombreArticlesNutriscoreDiffCommandesUser(utilisateur.getId(), "C");
		Long nbrD = ur.findNombreArticlesNutriscoreDiffCommandesUser(utilisateur.getId(), "D");
		Long nbrE = ur.findNombreArticlesNutriscoreDiffCommandesUser(utilisateur.getId(), "E");
		
		List<Categorie> cats = cr.findAll();
		
		String nomCat = "[";
		List<Long> numCat = new ArrayList<Long>();
		for(Categorie c : cats) {
			nomCat += "\""+c.getNomCategorie()+"\",";
			numCat.add( (Long) ur.findNombreArticlesCategorieUser(utilisateur.getId(), c.getNomCategorie()));
		}
		nomCat.substring(0, nomCat.length() - 1);
		nomCat += "]";
		 session.setAttribute("nomCats", nomCat);
		 session.setAttribute("numCats", numCat);
		
		final List<Article> articlesFrequentlyOrdered = articleRepository
				.findFrequentlyOrdered(utilisateur, 5);

		request.setAttribute("articlesCaroussel", articlesFrequentlyOrdered);
		session.setAttribute("bioArticle",nbrBioTotal);
		session.setAttribute("nonBioArticle",nbrTotal - nbrBioTotal);
		session.setAttribute("articleDash",nbrTotal);
		session.setAttribute("A",nbrA);
		session.setAttribute("B",nbrB);
		session.setAttribute("C",nbrC);
		session.setAttribute("D",nbrD);
		session.setAttribute("E",nbrE);

		view("dashboard/index", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}


}
