package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.HibernateUtil;
import models.Article;
import models.Composer;
import models.Panier;
import repositories.ArticleRepository;
import repositories.ComposerRepository;
import repositories.PanierRepository;

/**
 * Servlet implementation class GestionPreparation
 */
@WebServlet("/GestionPreparation")
public class GestionPreparation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionPreparation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Récupérer les paramètres de la requête
	        String idPanier = request.getParameter("idPanier");
	        String idArticle = request.getParameter("idArticle");
	        String quantite = request.getParameter("quantite");
	        
	        System.out.println("idPanier : " + idPanier);
	        System.out.println("idArticle : " + idArticle);
	        System.out.println("quantite : " + quantite);
	        
	        //Faire gestion updateBd
			final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			final Transaction transaction = session.beginTransaction();

			session.createSQLQuery("UPDATE Composer c "
							  + "SET c.qte = :quantite "
							  + "WHERE c.idArticle = :idArticle "
							  + "AND c.idPanier = :idPanier ").setParameter("quantite", quantite)
															  .setParameter("idArticle", idArticle)
															  .setParameter("idPanier", idPanier).executeUpdate();
			transaction.commit();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
