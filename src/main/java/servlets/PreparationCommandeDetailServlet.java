package servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Article;
import repositories.ArticleRepository;

/**
 * Servlet implementation class PreparationCommandeDetailServlet
 */
@WebServlet("/PreparationCommandeDetailServlet")
public class PreparationCommandeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreparationCommandeDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd;
		
		String idCommande = request.getParameter("idCommande");

		//TODO Surrement pas utile à verif
	//	PanierRepository panierRepository = new PanierRepository();
	//	final Panier panierAPreparer = panierRepository.findById(Integer.parseInt(idCommande));
		
		ArticleRepository articleRepository = new ArticleRepository();
		final List<Article> articles = articleRepository.findArticlePanier(Integer.parseInt(idCommande));
		
		
		request.setAttribute("articles", articles);
		

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
