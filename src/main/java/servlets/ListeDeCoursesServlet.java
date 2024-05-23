package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dtos.ListeCourseDto;
import mappers.ListeDeCourseMapper;
import models.Article;
import models.Contenir;
import models.ListeDeCourse;
import models.Utilisateur;
import repositories.ListeDeCourseRepository;

/**
 * Servlet implementation class Article
 */
@WebServlet("/listes_courses")
public class ListeDeCoursesServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	ListeDeCourseMapper listeDeCourseMapper = ListeDeCourseMapper.INSTANCE;
	ListeDeCourseRepository listeDeCourseRepository = new ListeDeCourseRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final HttpSession session = request.getSession();
		final List<String> errors = checkConnected(session);
		if (!errors.isEmpty()) {
			// Redirection avec message d'erreur dans le cas où l'utilisateur n'est pas
			// connecté
			session.setAttribute(ConnexionServlet.AUTH_ERRORS_KEY, errors);
			response.sendRedirect("ConnexionServlet");
			return;

		}

		final String redirectError = (String) session.getAttribute(ListeDeCourseShowServlet.LISTE_NOT_FOUND);
		if (redirectError != null) {
			ajouterErreur(redirectError, request);
		}

		// Récupération des listes de courses de l'utilisateur.
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
		final List<ListeCourseDto> listesDtos = recuperationListeUtilisateur(utilisateur);

		request.setAttribute("listes", listesDtos);

		view("liste_courses/index", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final HttpSession session = request.getSession();
		final List<String> errors = checkConnected(session);
		if (!errors.isEmpty()) {
			// Redirection avec message d'erreur dans le cas où l'utilisateur n'est pas
			// connecté
			session.setAttribute(ConnexionServlet.AUTH_ERRORS_KEY, errors);
			response.sendRedirect("ConnexionServlet");
			return;

		}

		final String action = request.getParameter("action");
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

		if ("transformToPanier".equals(action)) {
			processListeTransformToPanier(request, response, utilisateur);

		} else if ("delete".equals(action)) {
			processListeDelete(request, response);

		} else {
			processListeAdd(request, response, utilisateur);

		}

	}

	/**
	 * Gestion de la suppression d'une liste de course.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processListeDelete(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String idListe = request.getParameter("liste-courses-id");

		if (idListe == null || idListe.isBlank()) {
			ajouterErreur("Veuillez choisir une liste à transformer", request);
			responseGet(request, response);
			return;
		}

		final Session session = listeDeCourseRepository.getSession();
		final Transaction transaction = session.beginTransaction();

		final ListeDeCourse liste = listeDeCourseRepository.findById(Integer.parseInt(idListe), session);

		listeDeCourseRepository.delete(liste, session);

		transaction.commit();

		ajouterMessage(SUCCESSES_KEY, "La liste a été supprimée avec succès", request);
		doGet(request, response);
	}

	/**
	 * Gestion de la transformation d'une liste de course en panier.
	 *
	 * @param request
	 * @param response
	 * @param utilisateur
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void processListeTransformToPanier(final HttpServletRequest request, final HttpServletResponse response,
			final Utilisateur utilisateur) throws ServletException, IOException {


		final String idListe = request.getParameter("id");

		if (idListe == null || idListe.isBlank()) {
			ajouterErreur("Veuillez choisir une liste à transformer", request);
			responseGet(request, response);
			return;
		}

		// Check si le panier est déjà vide
		final HttpSession httpSession = request.getSession();

		final List<String> numeros = httpSession.getAttribute("numeros") == null
				? new ArrayList<>()
				: (List<String>) httpSession.getAttribute("numeros");

		if (!numeros.isEmpty()) {
			ajouterErreur("Vous ne pouvez pas transformer une liste tant qu'un panier est en cours", request);
			responseGet(request, response);
			return;
		}

		final Session session = listeDeCourseRepository.getSession();
		session.beginTransaction();
		final ListeDeCourse liste = listeDeCourseRepository.findById(Integer.parseInt(idListe), session);

		// Check si elle contient des post-it
		if (liste.getConcerners() != null && !liste.getConcerners().isEmpty()) {
			session.close();
			ajouterErreur("Cette liste contient des post-it et ne peut donc pas constituer un panier", request);
			responseGet(request, response);
			return;
		}


		final Map<Article, Contenir> contenirMap = liste.getContenirs();

		// Ajouter chaque article en session
		int nbrArticleTotal = 0;

		for (final Entry<Article, Contenir> entry : contenirMap.entrySet()) {
			final Article article = entry.getKey();
			final Contenir contenir = entry.getValue();

			final String idArticle = article.getId().toString();
			final Integer qte = contenir.getQte();

			numeros.add(idArticle);
			httpSession.setAttribute(idArticle, qte);
			nbrArticleTotal += qte;
		}

		session.close();

		// Check si elle contient au moins des articles
		if (nbrArticleTotal <= 0) {
			ajouterErreur("Cette liste ne comporte aucun article", request);
			responseGet(request, response);
			return;

		}

		httpSession.setAttribute("nbrArticleTotal", nbrArticleTotal);
		httpSession.setAttribute("numeros", numeros);

		// Redirect vers panier
		ajouterMessageSession(SUCCESSES_KEY, "La liste de course a été transformée en panier.");
		response.sendRedirect(request.getContextPath() + "/PanierServlet");

	}

	/**
	 * Gestion de l'ajout d'une liste de course.
	 *
	 * @param request
	 * @param response
	 * @param utilisateur
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processListeAdd(final HttpServletRequest request, final HttpServletResponse response,
			final Utilisateur utilisateur)
			throws ServletException, IOException {
		final String listeName = request.getParameter("listeName");

		if (listeName == null || listeName.isBlank()) {
			ajouterErreur("Vous devez saisir un nom non vide pour la liste", request);
			responseGet(request, response);
			return;
		}

		final ListeDeCourse listeDeCourse = new ListeDeCourse(listeName.strip(), utilisateur);
		listeDeCourseRepository.create(listeDeCourse);

		ajouterSucces("Liste ajoutée avec succès", request);

		request.setAttribute("addedListeId", listeDeCourse.getIdListDeCourse());

		doGet(request, response);
	}

	/**
	 * Récupération de la liste de courses de l'utilisateur.
	 *
	 * @param utilisateur
	 * @return
	 */
	private List<ListeCourseDto> recuperationListeUtilisateur(final Utilisateur utilisateur) {
		List<ListeCourseDto> resultList = new ArrayList<>();
		if (utilisateur != null) {
			final Session session = listeDeCourseRepository.getSession();
			session.beginTransaction();

			final List<ListeDeCourse> listes = listeDeCourseRepository.findByUtilisateurId(utilisateur.getId(),
					session);
			resultList = listes.stream()
					.map(listeDeCourseMapper::listeToDto)
					.toList();

			session.close();

		}

		return resultList;
	}

	/**
	 * Vérification si l'utilisateur est connecté.
	 *
	 * @param session
	 * @return
	 */
	private List<String> checkConnected(final HttpSession session) {
		final List<String> errors = new ArrayList<>();
		final Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

		if (utilisateur == null) {
			errors.add("Vous devez être connecté pour pouvoir afficher vos liste des courses");
		}

		return errors;
	}
}
