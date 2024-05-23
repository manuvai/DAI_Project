package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import models.Creneau;
import models.Magasin;
import models.Panier;
import models.Utilisateur;
import models.keys.ComposerKey;
import repositories.ArticleRepository;
import repositories.CreneauRepository;
import repositories.MagasinRepository;
import repositories.PanierRepository;
import repositories.StockerRepository;
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class VerifierDispo
 */
@WebServlet("/VerifierDispo")
public class VerifierDispo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PanierRepository pr = new PanierRepository();
	CreneauRepository cr = new CreneauRepository();
	UtilisateurRepository ur = new UtilisateurRepository();
	ArticleRepository ar = new ArticleRepository();
	StockerRepository sr = new StockerRepository();
	MagasinRepository mr = new MagasinRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifierDispo() {
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
		// recuperation des différentes infos
				doGet(request, response);
				String creneauS = request.getParameter("creneau");
				HttpSession session = request.getSession();
				session.setAttribute("creneau", creneauS);
				String magasin = (String) session.getAttribute("magasinRetrait");
				Utilisateur user = (Utilisateur) session.getAttribute("user");
				Creneau creneau = cr.findById(Integer.parseInt(creneauS));

				Panier p = new Panier(user, creneau);
				pr.create(p);
				ArrayList<String> numeros = (ArrayList<String>) session.getAttribute("numeros");
				
				List<Article> articlesManquants = new ArrayList<Article>();
				Date dateRetrait = creneau.getDateCreneau();
				// ajouter element au panier
				for (String num : numeros) {
					
					int stock = connaitreStockMagasinProduitStock(Integer.parseInt(magasin), Integer.parseInt(num),dateRetrait);

					int quantite = (int) session.getAttribute(num);
					
					if (quantite > stock) {
						articlesManquants.add(ar.findById(Integer.parseInt(num)));
					}
					else {
						Composer c = new Composer();
						c.setQte(quantite);
						ComposerKey composerKey = new ComposerKey(Integer.parseInt(num), p.getId());
						c.setKey(composerKey);
						p.getComposers().put(ar.findById(Integer.parseInt(num)), c);
					}
					session.removeAttribute(num);
				}
				numeros.clear();
				session.setAttribute("numeros", numeros);
				session.setAttribute("nbrArticleTotal", 0);
				// mettre a jour panier
				pr.update(p);
				session.setAttribute("panierAValider", p);
				
				session.setAttribute("articlesManquants", articlesManquants);
				
				RequestDispatcher rd = request.getRequestDispatcher("etapeFinale");
				rd.forward(request, response);
	}
	
	private int connaitreStockMagasinProduitStock(int idM, int idA, Date dateRetrait) throws ServletException, IOException {
		int stock = sr.getQuantiteByArticleAndMagasin(idA, idM);
		return stock;
		
	}

}
