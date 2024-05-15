

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/InscriptionServlet")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// Récupérer les paramètres du formulaire
	    String nom = request.getParameter("nom");
	    String prenom = request.getParameter("prenom");
	    String email = request.getParameter("email");
	    String confirmEmail = request.getParameter("confirmEmail");
	    String mdp = request.getParameter("mdp");
	    String confirmMdp = request.getParameter("confirmMdp");
		
	    if(!email.equals(confirmEmail)) {
	    
	    }
		
		HttpSession session = request.getSession();
		session.setAttribute("nom", nom);
        session.setAttribute("prenom", prenom);
        session.setAttribute("email", email);
        
		RequestDispatcher rd = request.getRequestDispatcher("inscriptionReussie");
		rd.forward(request, response);
		
	}

}
