package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Article;
import models.Creneau;
import models.Panier;
import models.Utilisateur;
import models.keys.ComposerKey;
import models.Composer;
import repositories.ArticleRepository;
import repositories.CreneauRepository;
import repositories.PanierRepository;
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class PayerServlet
 */
@WebServlet("/PayerServlet")
public class PayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PanierRepository pr = new PanierRepository();
	CreneauRepository cr = new CreneauRepository();
	UtilisateurRepository ur = new UtilisateurRepository();
	ArticleRepository ar = new ArticleRepository();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recuperation des différentes infos
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		int ptconso = (int) session.getAttribute("ptfidelConso");
		Panier p = (Panier) session.getAttribute("panierAValider");
		Map<Article, Composer> composition = p.getComposers();

		 List<Integer> produitsId = (List<Integer>) request.getSession().getAttribute("articleRempla");

	        
	     for (Integer articleId : produitsId) {
	            int qteAdd = Integer.parseInt(request.getParameter(""+articleId));
	            if(qteAdd!= 0) {
	            	Article article = ar.findById(articleId);
	            	if(composition.containsKey(article)) {
	            		composition.get(article).setQte(qteAdd + composition.get(article).getQte());
	            	}else {
	            		Composer c = new Composer();
						c.setQte(qteAdd);
						ComposerKey composerKey = new ComposerKey(articleId, p.getId());
						c.setKey(composerKey);
						p.getComposers().put(ar.findById(articleId), c);
	            	}
	            }


	        }
		
		double montantFinal = 0;
		
		for (Map.Entry<Article, Composer> entry : composition.entrySet()) {
            Article article = entry.getKey();
            Composer composer = entry.getValue();
            int quantity = composer.getQte(); 
            montantFinal += quantity*article.getPrixUnitaire()*(1+article.getPromotion()/100);
        }
		
		p.setComposers(composition);
		
		session.setAttribute("apayer", montantFinal);

		int ajoutFidelite = (int) (montantFinal / 5);
		user.setPtFidelite(user.getPtFidelite() - ptconso * 10 + ajoutFidelite);
		ur.update(user);
		
		
		pr.update(p);
		RequestDispatcher rd = request.getRequestDispatcher("panierEnregistrer");
		rd.forward(request, response);
	}

}
