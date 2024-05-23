package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repositories.ArticleRepository;
import repositories.CategorieRepository;
import repositories.RayonRepository;
import repositories.SousCategorieRepository;
import models.Article;
import models.Categorie;
import models.SousCategorie;

@WebServlet("/Catalogue")
public class CatalogueServlet extends AbstractServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArticleRepository repoArticles = new ArticleRepository();
	CategorieRepository repoCategories = new CategorieRepository();
	RayonRepository repoRayon = new RayonRepository();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Article> listeArticles;
		List<Categorie> listeCategories;
		List<SousCategorie> listeSousCat;
		
		if(request.getParameter("nomRayon")!=null) {
			listeArticles = repoArticles.getArticlesByRayonName(request.getParameter("nomRayon"));
			listeCategories = repoCategories.getCategoriesByRayonName(request.getParameter("nomRayon"));
		}
		
		else {
			listeArticles = repoArticles.findAll();
			listeCategories = repoCategories.findAll();
		}
		
		
		request.setAttribute("articles", listeArticles);
		request.setAttribute("categories", listeCategories);
		request.setAttribute("rayons", listeCategories);

		view("catalogue", request, response);
	}


	@Override
	protected void responseGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void responsePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
