

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Utilisateur;
import models.Utilisateur.Role;
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/InscriptionServlet")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscriptionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String nom = request.getParameter("nom");
	    String prenom = request.getParameter("prenom");
	    String email = request.getParameter("email");
	    String mdp = request.getParameter("mdp");
		
	    
		
		HttpSession session = request.getSession();
		session.setAttribute("nom", nom);
        session.setAttribute("prenom", prenom);
        session.setAttribute("email", email);
        
        boolean emailAlreadyExists =  utilisateurRepository.findMail(email);
        if(emailAlreadyExists) {
        	request.setAttribute("alreadyExist", true);
        	RequestDispatcher rd = request.getRequestDispatcher("inscription");
    		rd.forward(request, response);
        } else {
        	int mdpHashed = mdp.hashCode();
            Utilisateur utilisateur = new Utilisateur(nom, prenom, email, Integer.toString(mdpHashed), models.Utilisateur.Role.CLIENT);
            
            utilisateurRepository.create(utilisateur);
            
    		RequestDispatcher rd = request.getRequestDispatcher("inscriptionReussie");
    		rd.forward(request, response);
        }
        
		
	}

}
