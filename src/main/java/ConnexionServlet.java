

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Utilisateur;
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class ConnexionServlet
 */
@WebServlet("/ConnexionServlet")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UtilisateurRepository utilisateurRepository = new UtilisateurRepository(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionServlet() {
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
	    String email = request.getParameter("email");
	    String mdp = request.getParameter("mdp");

		HttpSession session = request.getSession();
		
       System.out.println(email + mdp);
        
        boolean emailAlreadyExists =  utilisateurRepository.findMail(email);
        if(emailAlreadyExists) {
        	int mdpHashed = mdp.hashCode();
            Utilisateur utilisateur = utilisateurRepository.findUserByMail(email);
            
            if(Integer.toString(mdpHashed).equals(utilisateur.getMotDePasse())) {
            	session.setAttribute("nom", utilisateur.getNom());
                session.setAttribute("prenom", utilisateur.getPrenom());
                session.setAttribute("email", email);
            	RequestDispatcher rd = request.getRequestDispatcher("home");
        		rd.forward(request, response);
            }else {
            	request.setAttribute("wrongMdp", true);
            	RequestDispatcher rd = request.getRequestDispatcher("connexion");
        		rd.forward(request, response);
            }
    		
        } else {
    		request.setAttribute("wrongMail", true);
        	RequestDispatcher rd = request.getRequestDispatcher("connexion");
    		rd.forward(request, response);
        }
        
		
	}

}
