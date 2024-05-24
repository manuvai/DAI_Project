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
import repositories.UtilisateurRepository;

/**
 * Servlet implementation class Article
 */
@WebServlet("/RecettesServlet")
public class RecettesServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	ListeDeCourseMapper listeDeCourseMapper = ListeDeCourseMapper.INSTANCE;
	ListeDeCourseRepository listeDeCourseRepository = new ListeDeCourseRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		UtilisateurRepository ur = new UtilisateurRepository();
		// Récupération des listes de courses de l'utilisateur.
		final Utilisateur utilisateur = ur.findById(31);
		final List<ListeCourseDto> listesDtos = recuperationListeUtilisateur(utilisateur);

		request.setAttribute("listes", listesDtos);

		view("recettes/index", request, response);

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

		final HttpSession session = request.getSession();
		final List<String> errors = checkConnected(session);
		if (!errors.isEmpty()) {
			// Redirection avec message d'erreur dans le cas où l'utilisateur n'est pas
			// connecté
			session.setAttribute(ConnexionServlet.AUTH_ERRORS_KEY, errors);
			response.sendRedirect("ConnexionServlet");
			return;

		}
		final ListeDeCourseRepository ldcR = new ListeDeCourseRepository();
		
		final String idListe = request.getParameter("id");
		
		final ListeDeCourse ldc = ldcR.findById(Integer.parseInt(idListe));

		final String redirectError = (String) session.getAttribute(ListeDeCourseShowServlet.LISTE_NOT_FOUND);
		if (redirectError != null) {
			ajouterErreur(redirectError, request);
		}

		// Récupération des listes de courses de l'utilisateur.
		final Utilisateur utilisateurReel = (Utilisateur) session.getAttribute("user");
		
		ldcR.addListeCourseToUtilisateurId(utilisateurReel.getId(), ldc);
		
		response.sendRedirect("listes_courses");
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
			errors.add("Vous devez être connecté pour ajouter une recette");
		}

		return errors;
	}
}
