package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AbstractServlet
 */
public abstract class AbstractServlet extends HttpServlet {

	public static final String ERRORS_KEY = "errors";
	private static final String UTF_8 = "UTF-8";

	@Override
	protected void doGet(
			final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding(UTF_8);

		responseGet(request, response);
	}

	/**
	 * Implémentation de la réponse GET
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void responseGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	@Override
	protected void doPost(
			final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding(UTF_8);

		responsePost(request, response);
	}

	/**
	 * Implémentation de la réponse POST
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void responsePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	/**
	 * Mise en place de la réponse pour un contenu au format XML
	 *
	 * @param response
	 */
	protected void xmlContent(final HttpServletResponse response) {
		if (Objects.isNull(response)) {
			return;
		}
		response.setContentType("application/xml;charset=" + UTF_8);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding(UTF_8);

	}

	/**
	 * Vide la valeur dans la session
	 *
	 * @param name Le nom de la clé en session
	 * @param session La session
	 */
	protected void viderSessionValue(final String name, final HttpSession session) {
		if (Objects.nonNull(session) && Objects.nonNull(name)) {
			session.setAttribute(name, null);
		}
	}

	/**
	 * Redirige la requête vers une page
	 *
	 * @param path Le chemin vers la vue
	 * @param request L'objet requête
	 * @param response L'objet de réponse
	 * @throws ServletException Dans le cas où une erreur est remontée
	 * @throws IOException Dans le cas où des erreurs de lecture sont remontées
	 */
	protected void view(final String path, final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		if (Objects.nonNull(path)) {

			forward("/WEB-INF/content/" + path + ".jsp"
					, request
					, response);
		}
	}

	/**
	 * Chaînage de la requête vers la route
	 *
	 * @param path La route de redirection
	 * @param request L'objet requête
	 * @param response L'objet de réponse
	 * @throws ServletException Dans le cas où une erreur est remontée
	 * @throws IOException Dans le cas où des erreurs de lecture sont remontées
	 */
	protected void forward(final String path, final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		if (Objects.nonNull(path)) {

			getServletContext()
			.getRequestDispatcher(path)
			.forward(request, response);
		}
	}

	/**
	 * Vide la variable contenant les erreurs
	 *
	 * @param request L'objet de requête
	 */
	protected void viderErreurs(final HttpServletRequest request) {
		if (Objects.nonNull(request)) {
			request.setAttribute(ERRORS_KEY, Collections.emptyList());
		}
	}

	/**
	 * Détermine si aucune erreur n'est présent
	 *
	 * @param request L'objet requête
	 * @return true si aucune erreur, false autrement
	 */
	@SuppressWarnings("unchecked")
	protected boolean isValid(final HttpServletRequest request) {
		return Objects.nonNull(request)
				&& Objects.nonNull(request.getAttribute(ERRORS_KEY))
				&& ((List<String>) request.getAttribute(ERRORS_KEY)).isEmpty();
	}

	/**
	 * Ajoute une erreur à la liste
	 *
	 * @param error L'erreur
	 * @param request L'objet de requête
	 */
	@SuppressWarnings("unchecked")
	protected void ajouterErreur(final String error,  final HttpServletRequest request) {
		if (Objects.nonNull(error) && Objects.nonNull(request)) {
			final List<String> erreurList = (List<String>) request.getAttribute(ERRORS_KEY);

			final List<String> nouvelleErreurList = Objects.isNull(erreurList)
					? new ArrayList<>()
					: new ArrayList<>(erreurList);

			nouvelleErreurList.add(error);

			request.setAttribute(ERRORS_KEY, nouvelleErreurList);
		}
	}

}
