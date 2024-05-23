package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approvisionner;
import models.Article;
import models.Commande;
import models.Commande.Etat;
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
        Commande commande = cr.findById(id);
        
        Map<Article, Approvisionner> articlesAdd = commande.getArticleApprovisionner();
        
        Magasin magasin = commande.getMagasin();
        Map<Article, Stocker> stockers = magasin.getStockers();
   

        // Update the commande status to 'LIVREE'
        commande.setEtat(Etat.LIVREE);

        // Save changes to the database
        cr.update(commande);
        RequestDispatcher rd = request.getRequestDispatcher("detailsCommandeFournisseur");
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
