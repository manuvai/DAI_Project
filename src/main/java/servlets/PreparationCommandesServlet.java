package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Panier;
import models.Panier.Etat;
import repositories.PanierRepository;

/**
 * Servlet implementation class preparationCommandesServlet
 */
@WebServlet("/PreparationCommandesServlet")
public class PreparationCommandesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			RequestDispatcher rd;
			PanierRepository panierRepository = new PanierRepository();
			final List<Panier> paniers = panierRepository.findPanierByStatut(Etat.VALIDEE);			
			
			String idCommande = request.getParameter("idCommande");
			
			request.setAttribute("idCommande", idCommande);
			request.setAttribute("paniers", paniers);
						
			//Tri par date heure pour afficher en premier les plus urgents
	        Collections.sort(paniers, new Comparator<Panier>() {
	            @Override
	            public int compare(Panier panier1, Panier panier2) {
	                int dateComparison = panier1.getCreneau().getDateCreneau().compareTo(panier2.getCreneau().getDateCreneau());
	                if (dateComparison != 0) {
	                    return dateComparison; // Si les dates sont différentes, retourne la comparaison de dates
	                } else {
	                    // Si les dates sont les mêmes, compare les heures des créneaux
	                    return panier1.getCreneau().getHeureCreneau().compareTo(panier2.getCreneau().getHeureCreneau());
	                }
	            }
	        });
	        
			rd = request.getRequestDispatcher("preparationcommandes");
			rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
