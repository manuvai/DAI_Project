package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Approvisionner;
import models.Article;
import models.Commande;
import models.Magasin;
import models.Composer;
import models.keys.ApprovisionnerKey;
import models.keys.ComposerKey;
import repositories.CommandeRepository;
import repositories.MagasinRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ValidationCommandeServlet
 */
@WebServlet("/ValidationCommandeServlet")
public class ValidationCommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CommandeRepository cr = new CommandeRepository();
    MagasinRepository mr = new MagasinRepository();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidationCommandeServlet() {
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
		String magasinSelectionne = request.getParameter("magasinSelectionne");
		Magasin magasin = mr.findById(Integer.parseInt(magasinSelectionne));
		String dateStr = request.getParameter("date");
        Date dateRetrait = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateRetrait = formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();

        // Récupérer la liste des articles depuis la session
        List<Article> articles = (List<Article>) request.getSession().getAttribute("articlesPourCommande");

        Commande commande = new Commande(dateRetrait, magasin);
        cr.create(commande);
        if (articles != null) {
            for (Article article : articles) {
                String quantiteParam = "quantite_" + article.getId();
                String quantiteStr = request.getParameter(quantiteParam);
                int quantite = 0;
                if (quantiteStr != null && !quantiteStr.isEmpty()) {
                    try {
                        quantite = Integer.parseInt(quantiteStr);
                        if (quantite > 0) {
                        	Approvisionner a = new Approvisionner();
                        	a.setQte(quantite);
                        	ApprovisionnerKey approvisionnerKey = new ApprovisionnerKey(article.getId(), commande.getId());   
                        	a.setKey(approvisionnerKey);
                        	
                        	commande.getArticleApprovisionner().put(article, a);
                        	}
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
               
            }
        }
        session.setAttribute("idLastCmd", commande.getId());

        cr.update(commande);
        // Rediriger ou traiter la commande comme nécessaire
        RequestDispatcher rd = request.getRequestDispatcher("commandeFournisseurEnvoyee");
		rd.forward(request, response);
    }

}
