package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GestionPanier
 */
@WebServlet("/GestionPanier")
public class GestionPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestionPanier() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer nbrArticleTotal = (Integer) session.getAttribute("nbrArticleTotal");

		if (nbrArticleTotal == null) {
			nbrArticleTotal = 0;
		}

		ArrayList<String> numeros = (ArrayList<String>) session.getAttribute("numeros");

		if (numeros == null) {
			// Si la liste n'existe pas encore en session, la créer
			numeros = new ArrayList<>();
			session.setAttribute("numeros", numeros);
		}

		String id = request.getParameter("idArticle");
		String ajouter = request.getParameter("ajouter");

		Integer nbrArticle = (Integer) session.getAttribute(id);
		if (nbrArticle == null) {
			nbrArticle = 0;
		}

		if (!numeros.contains(id)) {
			numeros.add(id);
		}

		if (ajouter.equals("true")) {
			session.setAttribute("nbrArticleTotal", 1 + nbrArticleTotal);
			session.setAttribute(id, 1 + nbrArticle);
			System.out.println("id " + id + " nbr " + nbrArticle);
		} else {
			session.setAttribute("nbrArticleTotal", nbrArticleTotal - 1);
			session.setAttribute(id, nbrArticle - 1);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
