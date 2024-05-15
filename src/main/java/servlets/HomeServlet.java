package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Rayon;
import repositories.RayonRepository;


@WebServlet("/home")
public class HomeServlet extends AbstractServlet {

	private static final long serialVersionUID = -2585229294607120381L;

	RayonRepository rayonRepository = new RayonRepository();
	
	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final List<Rayon> rayons = rayonRepository.findAll();
		request.setAttribute("rayons", rayons);
		
		view("home", request, response);
		
	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		responseGet(request, response);

	}

}
