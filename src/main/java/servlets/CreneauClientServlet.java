package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Creneau;
import models.Panier;
import repositories.CreneauRepository;
import repositories.PanierRepository;

/**
 * Servlet implementation class CreneauClientServlet
 */
@WebServlet("/CreneauClientServlet")
public class CreneauClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreneauClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    PanierRepository panierRepository = new PanierRepository();
	CreneauRepository creneauRepository = new CreneauRepository();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		
		String idCommande = request.getParameter("idCommande");
		
		
		Panier panier = panierRepository.findById(Integer.parseInt(idCommande));
		List<Creneau> creneaux = creneauRepository.findCreneauByMagasin(panier.getCreneau().getMagasin().getCodeMagasin());
		
		request.setAttribute("panier", panier);
		request.setAttribute("idCommande", idCommande);
		request.setAttribute("creneauxMagasin", creneaux);
		
		rd = request.getRequestDispatcher("modificationCreneau");
		rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		
	    String creneauS = request.getParameter("creneau");
	    if (creneauS == null || creneauS.isEmpty()) {
	        response.sendRedirect("errorPage.jsp");
	        return;
	    }
	    Creneau creneau = creneauRepository.findById(Integer.parseInt(creneauS));
	    if (creneau == null) {
	        response.sendRedirect("errorPage.jsp");
	        return;
	    }
	    
	    //Vérifier si creneau dispo
	    int nbrDispo = creneauRepository.findDisposParCreneau(creneau);
	     if (nbrDispo >0) {
	    	  String panierIdStr = request.getParameter("panierId");
	  	      Panier panier = panierRepository.findById(Integer.parseInt(panierIdStr));
	  	      if (panier == null) {
		        response.sendRedirect("errorPage.jsp");
		        return;
	  	      }

			   panier.setCreneau(creneau);
			   panierRepository.update(panier);
	     }
	 
	  
	
	     RequestDispatcher rd = request.getRequestDispatcher("confirmationCreneau");
		rd.forward(request, response);
	}

}
