package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

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
	public static final String INFOS_KEY = "infos";
	public static final String SUCCESSES_KEY = "successes";
	public static final String CSS_FILES_KEY = "cssFiles";
	public static final String CSS_LIBS_KEY = "cssLibs";
	public static final String JS_LIBS_KEY = "jsLibs";
	public static final String JS_FILES_KEY = "jsFiles";
	private static final String UTF_8 = "UTF-8";

	protected HttpServletRequest request;

	protected Properties properties;

	/**
	 * Récupération des propriétés.
	 *
	 * @return
	 */
	public Properties getProperties() {

		if (properties == null) {
			final Properties prop = new Properties();
			InputStream input = null;
			try {
				input = getClass().getClassLoader().getResourceAsStream("config.properties");

				// load a properties file
				prop.load(input);

				properties = prop;

			} catch (final IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return properties;
	}

	/**
	 * Récupération d'une information du fichier de propriétés.
	 *
	 * @param inKey
	 * @return
	 */
	public String getProperty(final String inKey) {
		String property = null;

		if (inKey != null) {
			final Properties prop = getProperties();

			if (prop != null) {
				property = prop.getProperty(inKey);
			}
		}

		return property;
	}
	@Override
	protected void doGet(
			final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding(UTF_8);

		this.request = request;

		mergeSessionMessages(request);
		viderMessagesSession();

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
	protected void ajouterErreur(final String error,  final HttpServletRequest request) {
		ajouterMessage(ERRORS_KEY, error, request);
	}

	/**
	 * Vide la variable contenant les erreurs
	 *
	 * @param request L'objet de requête
	 */
	protected void viderErreurs(final HttpServletRequest request) {
		viderMessages(ERRORS_KEY, request);
	}

	/**
	 * Ajoute un succès à la liste
	 *
	 * @param error   L'erreur
	 * @param request L'objet de requête
	 */
	protected void ajouterSucces(final String message, final HttpServletRequest request) {
		ajouterMessage(SUCCESSES_KEY, message, request);
	}

	/**
	 * Vide la variable contenant les succès
	 *
	 * @param request L'objet de requête
	 */
	protected void viderSucces(final HttpServletRequest request) {
		viderMessages(SUCCESSES_KEY, request);
	}

	/**
	 * Ajoute une info à la liste
	 *
	 * @param error   L'erreur
	 * @param request L'objet de requête
	 */
	protected void ajouterInfo(final String message, final HttpServletRequest request) {
		ajouterMessage(INFOS_KEY, message, request);
	}

	/**
	 * Vide la variable contenant les infos
	 *
	 * @param request L'objet de requête
	 */
	protected void viderInfos(final HttpServletRequest request) {
		viderMessages(INFOS_KEY, request);
	}

	/**
	 * Ajoute des messages au contexte partagé.
	 *
	 * @param key
	 * @param message
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	protected void ajouterMessage(final String key, final String message, final HttpServletRequest request) {
		final boolean isParametersValid = Objects.nonNull(key) && Objects.nonNull(message) && Objects.nonNull(request);

		if (isParametersValid) {
			final List<String> messageList = (List<String>) request.getAttribute(key);

			final List<String> nouvelleMessageList = Objects.isNull(messageList) ? new ArrayList<>()
					: new ArrayList<>(messageList);

			nouvelleMessageList.add(message);

			request.setAttribute(key, nouvelleMessageList);
		}

	}

	/**
	 * Vide le contenu des messages dans le contexte partagé.
	 *
	 * @param key
	 * @param request
	 */
	protected void viderMessages(final String key, final HttpServletRequest request) {
		if (Objects.nonNull(key) && Objects.nonNull(request)) {
			request.setAttribute(key, Collections.emptyList());
		}
	}

	/**
	 * Ajoute des messages à la session.
	 *
	 * @param key
	 * @param message
	 * @param request
	 */
	protected void ajouterMessageSession(final String key, final String message) {

		if (request != null) {
			ajouterMessageSession(key, message, request.getSession());
		}

	}

	/**
	 * Ajoute des messages à la session.
	 *
	 * @param key
	 * @param message
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	protected void ajouterMessageSession(final String key, final String message, final HttpSession session) {
		final boolean isParametersValid = Objects.nonNull(key) && Objects.nonNull(message) && Objects.nonNull(session);

		if (isParametersValid) {
			final List<String> messageList = (List<String>) session.getAttribute(key);

			final List<String> nouvelleMessageList = Objects.isNull(messageList) ? new ArrayList<>()
					: new ArrayList<>(messageList);

			nouvelleMessageList.add(message);

			session.setAttribute(key, nouvelleMessageList);
		}

	}

	/**
	 * Vide le contenu des messages dans le contexte partagé.
	 *
	 * @param key
	 * @param request
	 */
	protected void viderMessagesSession() {
		if (Objects.nonNull(request) && Objects.nonNull(request.getSession())) {
			final HttpSession session = request.getSession();

			final List<String> keys = Arrays.asList(SUCCESSES_KEY, INFOS_KEY, ERRORS_KEY);

			keys.forEach(key -> session.setAttribute(key, Collections.emptyList()));
		}
	}

	@SuppressWarnings("unchecked")
	private void mergeSessionMessages(final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		final List<String> keys = Arrays.asList(SUCCESSES_KEY, INFOS_KEY, ERRORS_KEY);

		for (final String key : keys) {
			final List<String> requestAttributes = (List<String>) request.getAttribute(key);
			final List<String> sessionAttributes = (List<String>) session.getAttribute(key);

			final List<String> newAttributes = new ArrayList<>();
			if (requestAttributes != null) {
				newAttributes.addAll(requestAttributes);

			}
			if (sessionAttributes != null) {
				newAttributes.addAll(sessionAttributes);

			}

			request.setAttribute(key, newAttributes);

		}
	}

}
