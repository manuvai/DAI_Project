package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import dtos.ListeCourseDto;
import mappers.ListeDeCourseMapper;
import models.ConnexionServlet;
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

		final String listeName = request.getParameter("listeName");

		if (listeName == null || listeName.isBlank()) {
			ajouterErreur("Vous devez saisir un nom non vide pour la liste", request);
			responseGet(request, response);
			return;
		}

		final Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
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
