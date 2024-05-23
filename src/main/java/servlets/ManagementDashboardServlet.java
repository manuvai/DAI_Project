package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Categorie;
import repositories.ArticleRepository;
import repositories.CategorieRepository;
import repositories.PanierRepository;

/**
 * Servlet implementation class ManagementDashboardServlet
 */
@WebServlet("/management/dashboard")
public class ManagementDashboardServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	PanierRepository panierRepository = new PanierRepository();
	ArticleRepository articleRepository = new ArticleRepository();
	CategorieRepository cr = new CategorieRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final HttpSession session = request.getSession();

		request.setAttribute("avgTempsPreparation", panierRepository.getAverageTempsPreparation());

		final Long nbrTotal = articleRepository.findNombreArticlesDiff();
		final Long nbrBioTotal = articleRepository.findNombreArticlesBio();
		final Long nbrA = articleRepository.findNombreArticlesNutriscoreDiff("A");
		final Long nbrB = articleRepository.findNombreArticlesNutriscoreDiff("B");
		final Long nbrC = articleRepository.findNombreArticlesNutriscoreDiff("C");
		final Long nbrD = articleRepository.findNombreArticlesNutriscoreDiff("D");
		final Long nbrE = articleRepository.findNombreArticlesNutriscoreDiff("E");

		final List<Categorie> cats = cr.findAll();

		StringBuilder nomCat = new StringBuilder("[");
		final List<Long> numCat = new ArrayList<>();
		for (final Categorie c : cats) {
			nomCat.append("\"").append(c.getNomCategorie()).append("\",");
			numCat.add(articleRepository.findNombreArticlesCategorie(c));
		}
		nomCat.append("]");
		session.setAttribute("nomCats", nomCat.toString());
		session.setAttribute("numCats", numCat);

		session.setAttribute("bioArticle", nbrBioTotal);
		session.setAttribute("nonBioArticle", nbrTotal - nbrBioTotal);
		session.setAttribute("articleDash", nbrTotal);
		session.setAttribute("A", nbrA);
		session.setAttribute("B", nbrB);
		session.setAttribute("C", nbrC);
		session.setAttribute("D", nbrD);
		session.setAttribute("E", nbrE);
		view("management/dashboard", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
