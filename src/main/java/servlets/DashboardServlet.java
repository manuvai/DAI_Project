package servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	ArticleRepository articleRepository = new ArticleRepository();

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


		final List<Article> articlesFrequentlyOrdered = articleRepository
				.findFrequentlyOrdered(utilisateur, 5);

		request.setAttribute("articlesCaroussel", articlesFrequentlyOrdered);

		view("dashboard/index", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}


}
