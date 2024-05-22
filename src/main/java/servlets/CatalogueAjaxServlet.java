package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class CatalogueAjaxServlet
 */
@WebServlet("/CatalogueAjaxServlet")
public class CatalogueAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogueAjaxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticleRepository repoArticles = new ArticleRepository();
		List<Article> listeArticles;
		response.setContentType("application/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try (PrintWriter out = response.getWriter()){
			/*----- Ecriture de la page XML -----*/
			out.println("<?xml version=\"1.0\"?>");
			out.println("<liste_articles>");

			/*----- Récupération des paramètres -----*/
			//PAR SOUS-CATEGORIE
			if(request.getParameter("sousCategorie")!=null) {
				listeArticles = repoArticles.getArticlesBySousCategorieName(request.getParameter("sousCategorie"));
			}
			
			//PAR CATEGORIE
			else if(request.getParameter("categorie")!=null) {
				listeArticles = repoArticles.getArticlesByCategorieName(request.getParameter("categorie"));
			}
			
			//PAR RAYON
			else if(request.getParameter("rayon")!=null) {
				listeArticles = repoArticles.getArticlesByRayonName(request.getParameter("rayon"));
			}
			
			else listeArticles = new ArrayList<Article>();
			
			for(Article article : listeArticles) {
				
				System.out.println(article.getLib());
				out.println("<article>");
					out.println("<nomArticle>"+article.getLib()+"</nomArticle>");
					out.println("<imgArticle>"+article.getCheminImage()+"</imgArticle>");
					out.println("<prixArticle>"+article.getPrixUnitaire()+"</prixArticle>");
					out.println("<poidsArticle>"+article.getPoids()+"</poidsArticle>");
					out.println("<isBioArticle>"+article.getBio()+"</isBioArticle>");
					out.println("<promotionArticle>"+article.getPrixApresPromotion()+"</promotionArticle>");
				out.println("</article>");
			}
			out.println("</liste_articles>");
		}
		catch (Exception ex){
			PrintWriter out = response.getWriter();
			out.println("<article>Erreur - " + ex.getMessage() + "</article>");
			out.println("</liste_articles>");
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
