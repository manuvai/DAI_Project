package servlets;


import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class ConnexionServlet
 */
@WebServlet("/ConnexionServlet")
public class ConnexionServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	public static final String AUTH_ERRORS_KEY = "authErrors";
	UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final HttpSession session = request.getSession();
		final List<String> errors = (List<String>) session.getAttribute(AUTH_ERRORS_KEY);

		if (errors != null) {
			errors.forEach(error -> ajouterErreur(error, request));
		}
		session.setAttribute(AUTH_ERRORS_KEY, null);

		view("connexion", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
	    final String email = request.getParameter("email");
	    final String mdp = request.getParameter("mdp");

		final HttpSession session = request.getSession();

       System.out.println(email + mdp);

        final boolean emailAlreadyExists =  utilisateurRepository.findMail(email);
        if(emailAlreadyExists) {
        	final int mdpHashed = mdp.hashCode();
            final Utilisateur utilisateur = utilisateurRepository.findUserByMail(email);

            if(Integer.toString(mdpHashed).equals(utilisateur.getMotDePasse())) {
            	session.setAttribute("nom", utilisateur.getNom());
                session.setAttribute("prenom", utilisateur.getPrenom());
                session.setAttribute("email", email);
                session.setAttribute("role",  utilisateur.getRole());
                session.setAttribute("user",utilisateur);

                final Role role = utilisateur.getRole();
                RequestDispatcher rd;
	                if (role.equals(Role.CLIENT)){
	                	rd = request.getRequestDispatcher("home");
	                    rd.forward(request, response);
	                } else if (role.equals(Role.GESTIONNAIRE)) {
	                	response.sendRedirect("management/");
	                } else {
	                	rd = request.getRequestDispatcher("PreparationCommandesServlet");
	                	rd.forward(request, response);
	                }



            }else {
            	request.setAttribute("wrongMdp", true);
            	final RequestDispatcher rd = request.getRequestDispatcher("connexion");
        		rd.forward(request, response);
            }

        } else {
    		request.setAttribute("wrongMail", true);
        	final RequestDispatcher rd = request.getRequestDispatcher("connexion");
    		rd.forward(request, response);
        }


	}

}
