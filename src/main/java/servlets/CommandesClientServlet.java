package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Panier;
import models.Utilisateur;
import repositories.PanierRepository;
/**
 * Servlet implementation class CommandesClientServlet
 */
@WebServlet("/CommandesClientServlet")
public class CommandesClientServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	PanierRepository panierRepository = new PanierRepository();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Retrieve user id
		final HttpSession session = request.getSession();
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        
		if (utilisateur == null) {
	        try {
	        	view("home", request, response);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return;
	    }
		
		final List<Panier> commandes = panierRepository.findPanierByUserId(utilisateur.getId());
		
		request.setAttribute("commandes", commandes);
	
		view("commandesClient", request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
