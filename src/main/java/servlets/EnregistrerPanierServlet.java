package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Composer;
import models.Panier;
import models.Utilisateur;
import models.keys.ComposerKey;
import repositories.ArticleRepository;
import repositories.PanierRepository;

/**
 * Servlet implementation class EnregistrerPanierServlet
 */
@WebServlet("/EnregistrerPanierServlet")
public class EnregistrerPanierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArticleRepository article = new ArticleRepository();
	PanierRepository panierRepository = new PanierRepository();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Récupérer data en session
		HttpSession session = request.getSession();
		ArrayList<String> numeros = (ArrayList<String>) session.getAttribute("numeros");
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		
		if (user != null) {
			// Creer un panier en statut enregistrer 
			Panier panier = new Panier();
			panier.setUtilisateur(user);
			panier.setEtat(Panier.Etat.ENREGISTRE);
			panier.setComposers(new HashMap<>());
			panierRepository.create(panier);
			
			// Ajouter articles au panier
			for (String num : numeros) {
				Composer composer = new Composer();
				composer.setQte((int) session.getAttribute(num));
				ComposerKey composerKey = new ComposerKey(Integer.parseInt(num), panier.getId());
				composer.setKey(composerKey);

				panier.getComposers().put(article.findById(Integer.parseInt(num)), composer);
				session.removeAttribute(num);
			}
			numeros.clear();
			session.setAttribute("numeros", numeros);
			session.setAttribute("nbrArticleTotal", 0);
			panierRepository.update(panier);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("confirmationEnregistrerPanier");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("connexion");
			rd.forward(request, response);
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
