package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Approvisionner;
import models.Article;
import models.Commande;
import models.Commande.Etat;
import models.keys.StockerKey;
import models.Magasin;
import models.Stocker;
import repositories.CommandeRepository;

/**
 * Servlet implementation class ValiderRecuCommande
 */
@WebServlet("/ValiderRecuCommande")
public class ValiderRecuCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;

    CommandeRepository cr = new CommandeRepository();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValiderRecuCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        
        //recuperation commande
        Session session = cr.getSession();
        Transaction t =  session.beginTransaction();
        Commande commande = cr.findById(id, session);
        
        //articles de la commande
        Map<Article, Approvisionner> articlesAdd = commande.getArticleApprovisionner();
        
        //magasin
        Magasin magasin = commande.getMagasin();
        
        //stock actuel
        Map<Article, Stocker> stockers = magasin.getStockers();
        
        for (Map.Entry<Article, Approvisionner> entry : articlesAdd.entrySet()) {
            Article article = entry.getKey();
            int quantityToAdd = entry.getValue().getQte();

            Stocker stocker = stockers.get(article);
            if (stocker != null) {
                // If the article is already in stock, increase the quantity
                stocker.setQuantite(stocker.getQuantite() + quantityToAdd);
            } else {
                // If the article is not in stock, create a new stocker entry
                stocker = new Stocker();
                stocker.setQuantite(quantityToAdd);
                StockerKey stockerKey = new StockerKey(magasin.getCodeMagasin(), article.getId());
                stocker.setKey(stockerKey);
                stockers.put(article, stocker);
                
            }
        }
        // Update the commande status to 'LIVREE'
        commande.setEtat(Etat.LIVREE);

        // Save changes to the database
        cr.update(commande, session);
        t.commit();
        RequestDispatcher rd = request.getRequestDispatcher("voirCommande");
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
