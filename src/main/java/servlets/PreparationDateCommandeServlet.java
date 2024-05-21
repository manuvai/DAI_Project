package servlets;



import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.HibernateUtil;
import models.Article;
import models.Composer;
import models.Panier;
import models.Panier.Etat;
import repositories.ArticleRepository;
import repositories.ComposerRepository;
import repositories.PanierRepository;

/**
 * Servlet implementation class PreparationDateCommandeServlet
 */
@WebServlet("/PreparationDateCommandeServlet")
public class PreparationDateCommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	PanierRepository pr = new PanierRepository();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreparationDateCommandeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		
		String idPanier = request.getParameter("idPanier");
		String dateJs = request.getParameter("DateFin");
		
		Panier panier = pr.findById(Integer.parseInt(idPanier));
		Date date;
		
		
		
		if (dateJs == null) {
			date = new Date(Long.parseLong(request.getParameter("DateDebut")));
			panier.setDateDebutPreparation(date);
		} else {
			date = new Date(Long.parseLong(dateJs));
			panier.setDateFinPreparation(date);
			panier.setEtat(Etat.PRETE);
		}
		
		pr.update(panier);
		
		rd = request.getRequestDispatcher("preparationdetail");
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
