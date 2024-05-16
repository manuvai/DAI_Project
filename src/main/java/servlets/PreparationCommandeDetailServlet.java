package servlets;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Article;
import models.Composer;
import repositories.ArticleRepository;
import repositories.ComposerRepository;

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
		
		ArticleRepository articleRepository = new ArticleRepository();
		final List<Article> articles = articleRepository.findArticlePanier(Integer.parseInt(idCommande));
		
		ComposerRepository composerRepository = new ComposerRepository();
		final List<Composer> composers = composerRepository.findArticlePanier(Integer.parseInt(idCommande));
		
		Map<Article, Integer> mapArticles = new HashMap<>();
		
        // Ajout <Article, Qte> à la Map
        for (Composer composer : composers) {
            for (Article article : articles) {
                if (composer.getKey().getIdArticle() == article.getId()) {
                    mapArticles.put(article, composer.getQte());
                    break;
                }
            }
        }
		
		request.setAttribute("articlesQte", mapArticles);
		
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
