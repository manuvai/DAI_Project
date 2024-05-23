package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Article;
import models.Composer;
import models.Panier;
import models.Utilisateur;
import models.keys.ComposerKey;
import repositories.PanierRepository;
import repositories.ArticleRepository;

/**
 * Servlet implementation class ReprendrePanierServlet
 */
@WebServlet("/ReprendrePanierServlet")
public class ReprendrePanierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PanierRepository panierRepository = new PanierRepository();
	ArticleRepository articleRepository = new ArticleRepository();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReprendrePanierServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Récupérer data en session
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		
		//Récupérer l'id du panier
		final String idPanier = request.getParameter("idPanier");
		Panier panier = panierRepository.findById(Integer.parseInt(idPanier));

		if (user != null) {
			
			//Supprimer le panier en session
			ArrayList<String> numeros = (ArrayList<String>) session.getAttribute("numeros");
			if (numeros == null) {
				// Si la liste n'existe pas encore en session, la créer
				numeros = new ArrayList<>();
				session.setAttribute("numeros", numeros);
			} else {
				for (String num : numeros) {
					session.removeAttribute(num);
				}
				numeros.clear();
				session.setAttribute("numeros", numeros);
			}
			
			Integer nbrArticleTotal = (Integer) session.getAttribute("nbrArticleTotal");
			if (nbrArticleTotal == null) {
				nbrArticleTotal = 0;
			} else {
				session.setAttribute("nbrArticleTotal", 0);
			}
			
			
			//Remplacer le panier en session
			List<Article> articles = articleRepository.findArticlePanier(Integer.parseInt(idPanier));
			int totalArticles =0;
			for (Article article : articles) {
				numeros.add(Integer.toString(article.getId()));
				session.setAttribute(Integer.toString(article.getId()),Integer.toString(panier.getComposers().get(article).getQte()));
				totalArticles =+ panier.getComposers().get(article).getQte();
				
			}
			session.setAttribute("numeros", numeros);
			session.setAttribute("nbrArticleTotal", totalArticles);
			
			//Supprimer le panier en statut enregistré
			panierRepository.delete(panier);
			
			
			
			RequestDispatcher rd = request.getRequestDispatcher("panier");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("connexion");
			rd.forward(request, response);
		}
	}

}
