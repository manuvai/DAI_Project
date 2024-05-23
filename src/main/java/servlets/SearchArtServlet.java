package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Article;
import repositories.ArticleRepository;

/**
 * Servlet implementation class SearchArtServlet
 */
@WebServlet("/SearchArtServlet")
public class SearchArtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArticleRepository ar = new ArticleRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchArtServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*----- Lecture de la requête en UTF-8 -----*/
		request.setCharacterEncoding("UTF-8");

		/*----- Type de la réponse -----*/
		response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter())
			{
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
			out.println("<liste>");

			/*----- Récupération des paramètres -----*/
			String pattern = request.getParameter("pattern");

			/*----- Lecture de liste de mots dans la BD -----*/
			List<Article> articles = ar.search(pattern);

			for (Article a : articles)
				out.println("<article><lib><![CDATA[" + a.getLib() + "]]></lib><id>" + a.getId() + "</id></article>");

			out.println("</liste>");
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
