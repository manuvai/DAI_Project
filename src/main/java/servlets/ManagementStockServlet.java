package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dtos.ArticleStockDto;
import models.Magasin;
import repositories.ArticleRepository;
import repositories.MagasinRepository;

/**
 * Servlet implementation class ManagementStock
 */
@WebServlet("/management/stock")
public class ManagementStockServlet extends AbstractServlet {

	private static final long serialVersionUID = 7770262801867382923L;

	MagasinRepository magasinRepository = new MagasinRepository();
	public ArticleRepository articleRepository = new ArticleRepository();

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		responseGet(request, response);

	}

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Checks
		final List<Magasin> magasins = magasinRepository.findAll();
		request.setAttribute("magasins", magasins);

		final String magasinId = request.getParameter("magasin-id");

		final List<ArticleStockDto> articlesDtos = articleRepository.extractArticleStock(magasinId);

		request.setAttribute("articles", articlesDtos);

		view("management/stock", request, response);

	}

}
