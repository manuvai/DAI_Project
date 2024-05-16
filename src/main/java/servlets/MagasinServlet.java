package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		request.setAttribute("magasins", magasins);
		view("choixMagasin", request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String magasinSelectionne = request.getParameter("magasinSelectionne");
        
        if (magasinSelectionne != null && !magasinSelectionne.isEmpty()) {

            HttpSession session = request.getSession();
            session.setAttribute("magasinRetrait", magasinSelectionne);
            
            response.sendRedirect("choixCreneaux.jsp"); 
        } else {
            //TODO : gérer erreur
        }
        
        //TODO: redirection vers choix des creneaux
        response.sendRedirect("choixCreneaux.jsp"); 
	}


	@Override
	protected void responsePost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
