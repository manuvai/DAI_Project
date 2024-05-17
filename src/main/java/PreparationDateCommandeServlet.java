

import java.io.IOException;
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
import repositories.ArticleRepository;
import repositories.ComposerRepository;

/**
 * Servlet implementation class PreparationDateCommandeServlet
 */
@WebServlet("/PreparationDateCommandeServlet")
public class PreparationDateCommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		String date = request.getParameter("DateFin");
		String dateEtat;
		
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();

		if (date.isEmpty()) {
			dateEtat = "DateDebutPreparation";
			date = request.getParameter("DateDebut");
		} else {
			dateEtat = "DateFinPreparation";
		}
		
		session.createQuery("UPDATE Panier "
				 + "SET :dateEtat = :date"
				 + "WHERE Panier.IdPanier = :idPanier").setParameter("idPanier", idPanier).setParameter("dateEtat", dateEtat).setParameter("date", date);
	
		transaction.commit();
		
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
