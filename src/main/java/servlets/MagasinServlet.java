package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Magasin;
import repositories.MagasinRepository;

/**
 * Servlet implementation class MagasinServlet
 */
@WebServlet("/MagasinServlet")
public class MagasinServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
	MagasinRepository magasinRepository = new MagasinRepository();
	
	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final List<Magasin> magasins = magasinRepository.findAll();
		System.out.println("!!!!!!!!!");
		System.out.println(magasins);
		request.setAttribute("magasins", magasins);
		request.setAttribute("lol", "lol");
		view("choixMagasin", request, response);

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	@Override
	protected void responsePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
