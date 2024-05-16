package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repositories.ArticleRepository;
import repositories.CategorieRepository;
import models.Article;
import models.Categorie;

@WebServlet("/Catalogue")
public class CatalogueServlet extends AbstractServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArticleRepository repoArticles = new ArticleRepository();
	CategorieRepository repoCategories = new CategorieRepository();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Article> listeArticles;
		List<Categorie> listeCategories;
		
		if(request.getParameter("nomRayon")!=null) {
			listeArticles = repoArticles.getArticlesByRayonName(request.getParameter("nomRayon"));
		}
		
		else {
			listeArticles = repoArticles.findAll();
		}
		
		listeCategories = repoCategories.findAll();
		
		request.setAttribute("articles", listeArticles);
		request.setAttribute("categories", listeCategories);

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
