package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dtos.ArticleListDto;
import models.Article;
import repositories.ArticleRepository;

/**
 * Servlet implementation class SearchArticles
 */
@WebServlet("/articles/search")
public class SearchArticlesServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	ArticleRepository articleRepository = new ArticleRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String query = request.getParameter("q");

		final List<Article> articles = articleRepository.search(query);
		final ArticleListDto resultListDto = new ArticleListDto(articles);

		xmlContent(response);

		try (PrintWriter out = response.getWriter()) {
			out.println("<?xml version=\"1.0\"?>");
			out.println(resultListDto.toString());
		}

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String query = request.getParameter("query");

		final List<Article> articles = articleRepository.search(query);

		request.setAttribute("articlesCaroussel", articles);

		view("search", request, response);

	}

}
