package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Article;
import models.Magasin;
import models.Utilisateur;
import repositories.ArticleRepository;
import repositories.MagasinRepository;

/**
 * Servlet implementation class ValiderPanierServlet
 */
@WebServlet("/ValiderPanierServlet")
public class ValiderPanierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ArticleRepository articleRepository = new ArticleRepository();
    MagasinRepository magasinRepository = new MagasinRepository();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValiderPanierServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArrayList<String> numeros = (ArrayList<String>) session.getAttribute("numeros");
		ArrayList<Magasin> magasins = (ArrayList<Magasin>) magasinRepository.findAll();
		session.setAttribute("tousMagasins", magasins);
		
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		 if(user != null) {
			 double total = 0;
				
				for(String num: numeros) {
					Article a = (Article) articleRepository.findById(Integer.parseInt(num));
					int qte = (int) session.getAttribute(num);
					total = total + a.getPrixUnitaire()*qte*(1-a.getPromotion()/100);
				}
				session.setAttribute("totalPanierValidation", total);
				
				RequestDispatcher rd = request.getRequestDispatcher("validerPanier");
				rd.forward(request, response);
		 }else {
			 RequestDispatcher rd = request.getRequestDispatcher("connexion");
				rd.forward(request, response);
		 }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
