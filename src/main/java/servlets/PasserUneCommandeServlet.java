package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Article;
import models.Magasin;
import repositories.ArticleRepository;
import repositories.MagasinRepository;

/**
 * Servlet implementation class PasserUneCommandeServlet
 */
@WebServlet("/PasserUneCommandeServlet")
public class PasserUneCommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArticleRepository ar = new ArticleRepository();
	MagasinRepository mr = new MagasinRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasserUneCommandeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Article> articles = ar.findAll();
		List<Magasin> magasins = mr.findAll();
		final HttpSession session = request.getSession();
		
		session.setAttribute("articlesPourCommande", articles);
		
		
		session.setAttribute("magasinsPourCommande", magasins);
		
		final RequestDispatcher rd = request.getRequestDispatcher("commandePourMagasin");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
