package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repositories.ArticleRepository;
import models.Article;

@WebServlet("/Catalogue")
public class CatalogueServlet extends AbstractServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArticleRepository repoArticles = new ArticleRepository();
	
	@Override
	protected void responseGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Article> listeArticles = repoArticles.findAll();
		request.setAttribute("articles", listeArticles);

		view("catalogue", request, response);

	}

	@Override
	protected void responsePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		responseGet(request, response);

	}

}
