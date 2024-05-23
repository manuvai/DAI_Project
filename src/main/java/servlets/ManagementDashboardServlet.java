package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repositories.PanierRepository;

/**
 * Servlet implementation class ManagementDashboardServlet
 */
@WebServlet("/management/dashboard")
public class ManagementDashboardServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	PanierRepository panierRepository = new PanierRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("avgTempsPreparation", panierRepository.getAverageTempsPreparation());
		view("management/dashboard", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
