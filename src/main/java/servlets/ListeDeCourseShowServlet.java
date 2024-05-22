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
import org.hibernate.Transaction;

import dtos.ListeCourseDto;
import mappers.ListeDeCourseMapper;
import models.Article;
import models.Concerner;
import models.Contenir;
import models.ListeDeCourse;
import models.PostIt;
import models.keys.ConcernerKey;
import models.keys.ContenirKey;
import repositories.ArticleRepository;
import repositories.ContenirRepository;
import repositories.ListeDeCourseRepository;
import repositories.PostItRepository;

/**
 * Servlet implementation class ListeDeCourseShow
 */
@WebServlet("/listes_courses/show")
public class ListeDeCourseShowServlet extends AbstractServlet {
	public static final String LISTE_NOT_FOUND = "listeNotFound";

	private static final long serialVersionUID = 1L;

	ListeDeCourseRepository listeDeCourseRepository = new ListeDeCourseRepository();
	PostItRepository postItRepository = new PostItRepository();
	ContenirRepository contenirRepository = new ContenirRepository();
	ArticleRepository articleRepository = new ArticleRepository();
	ListeDeCourseMapper mapper = ListeDeCourseMapper.INSTANCE;

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Integer idListe = request.getParameter("id") == null || request.getParameter("id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("id"));

		final HttpSession httpSession = request.getSession();
		if (idListe == null) {
			httpSession.setAttribute(LISTE_NOT_FOUND, "La liste demandée n'existe pas");
			response.sendRedirect(request.getContextPath() + "/listes_courses");
			return;
		}

		final Integer addedPostItId = (Integer) httpSession.getAttribute("addedPostItId");

		if (addedPostItId != null) {
			request.setAttribute("addedPostItId", addedPostItId);
			ajouterSucces("Le post-it a bien été ajouté à votre liste", request);
			httpSession.setAttribute("addedPostItId", null);
		}

		final Session session = listeDeCourseRepository.getSession();
		session.beginTransaction();

		final ListeDeCourse liste = listeDeCourseRepository.findById(idListe, session);
		final ListeCourseDto listeDto = mapper.listeToDto(liste);

		session.close();

		request.setAttribute("liste", listeDto);

		view("liste_courses/show", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String action = request.getParameter("action");

		if (action == null) {
			response.sendRedirect(request.getContextPath() + "/listes_courses");
			return;
		}

		if ("delete".equals(action)) {
			processDelete(request, response);

		} else if ("add".equals(action)) {
			processAdd(request, response);

		} else if ("replacePostIt".equals(action)) {
			processReplace(request, response);

		} else if ("editQtyArticle".equals(action)) {
			processEditQty(request, response);

		} else {
			response.sendRedirect(request.getContextPath() + "/listes_courses");

		}
	}

	/**
	 * Gestion de la MAJ de la quantité d'un article.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processEditQty(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération des variables
		final Integer listeId = request.getParameter("id") == null || request.getParameter("id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("id"));
		final Integer articleId = request.getParameter("article-id") == null
					|| request.getParameter("article-id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("article-id"));

		final Integer qty = request.getParameter("qty") == null || request.getParameter("qty").isBlank()
				? null
				: Integer.parseInt(request.getParameter("qty"));

		if (listeId == null || articleId == null || qty == null) {
			ajouterErreur("Une erreur est survenue veuillez réessayer ultérieurement", request);
			doGet(request, response);
			return;
		}

		final Session session = listeDeCourseRepository.getSession();
		final Transaction transaction = session.beginTransaction();

		final Article article = articleRepository.findById(articleId, session);
		final ListeDeCourse liste = listeDeCourseRepository.findById(listeId, session);
		final Contenir contenir = liste.getContenirs().get(article);

		if (contenir == null) {
			ajouterErreur("Cet article ne fait pas partie de cette liste", request);

			session.close();
			doGet(request, response);
			return;
		}

		String message;
		if (qty <= 0) {
			message = "L'article a bien été retiré de la liste";
			liste.getContenirs().remove(article);
			contenirRepository.delete(contenir, session);

		} else {
			message = "La quantité a été modifiée avec succès";
			contenir.setQte(qty);
			liste.getContenirs().put(article, contenir);
		}

		listeDeCourseRepository.update(liste, session);

		transaction.commit();

		ajouterSucces(message, request);
		responseGet(request, response);

	}

	/**
	 * Gestion de la transformation d'une post-it en article.
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void processReplace(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération des variables
		final Integer listeId = request.getParameter("id") == null || request.getParameter("id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("id"));
		final Integer articleId = request.getParameter("article-id") == null
					|| request.getParameter("article-id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("article-id"));
		final Integer postItId = request.getParameter("postIt-id") == null
				|| request.getParameter("postIt-id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("postIt-id"));

		if (listeId == null || articleId == null || postItId == null) {
			ajouterErreur("Une erreur est survenue veuillez réessayer ultérieurement", request);
			doGet(request, response);
			return;
		}

		final Session session = listeDeCourseRepository.getSession();
		final Transaction transaction = session.beginTransaction();

		final ListeDeCourse liste = listeDeCourseRepository.findById(listeId, session);
		// Suppression de la post-it à la liste
		final PostIt postIt = postItRepository.findById(postItId, session);
		final Concerner concerner = postIt.getStockers().get(liste);
		final int qty = concerner.getQuantitePostIt();

		postItRepository.delete(postIt, session);

		// Ajout de l'article à la liste
		final Article article = articleRepository.findById(articleId, session);
		final ContenirKey key = new ContenirKey(articleId, listeId);
		final Contenir contenir = new Contenir();
		contenir.setKey(key);
		contenir.setQte(qty);

		article.getContenirs().put(liste, contenir);

		articleRepository.update(article, session);

		transaction.commit();

		ajouterSucces("La post-it a été remplacée avec succès", request);
		responseGet(request, response);
	}

	/**
	 * Gestion de la suppression d'une post-it
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final Integer postItId = Integer.parseInt(request.getParameter("post-it-id"));
		final Integer listeId = Integer.parseInt(request.getParameter("id"));

		final List<String> errors = checkDelete(postItId);

		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			responseGet(request, response);
			return;
		}

		final Session session = postItRepository.getSession();

		final Transaction transaction = session.beginTransaction();
		final PostIt postIt = postItRepository.findById(postItId, session);

		postItRepository.delete(postIt, session);

		transaction.commit();

		response.sendRedirect(request.getContextPath() + "/listes_courses/show?id=" + listeId);

	}

	/**
	 * Gestion de l'ajout d'une post-it
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void processAdd(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, ServletException {

		final Integer listeId = request.getParameter("id") == null || request.getParameter("id").isBlank()
				? null
				: Integer.parseInt(request.getParameter("id"));

		final String postItName = request.getParameter("postItName");

		final Integer qty = request.getParameter("qty") == null || request.getParameter("qty").isBlank()
				? null
				: Integer.parseInt(request.getParameter("qty"));

		final List<String> errors = checkAdd(listeId, postItName, qty);

		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			responseGet(request, response);
			return;
		}

		final PostIt postIt = new PostIt();
		postIt.setLabel(postItName.strip());

		postItRepository.create(postIt);

		linkListeDeCourse(listeId, qty, postIt.getIdPostIt());

		final HttpSession httpSession = request.getSession();
		httpSession.setAttribute("addedPostItId", postIt.getIdPostIt());

		response.sendRedirect(request.getContextPath() + "/listes_courses/show?id=" + listeId);
	}

	/**
	 * Vérification des paramètres pour l'ajout d'une postIt
	 *
	 * @param listeId
	 * @param postItName
	 * @param qty
	 * @return
	 */
	private List<String> checkAdd(final Integer listeId, final String postItName, final Integer qty) {
		final List<String> errors = new ArrayList<>();

		if (listeId == null) {
			errors.add("Une erreur est survenue, veuillez réessayer");
		} else if (postItName == null || "".equals(postItName)) {
			errors.add("Vous devez fournir un nom non vide");
		} else if (qty == null || qty <= 0) {
			errors.add("Veuillez vérifier la quantité fournie");
		}

		return errors;
	}

	/**
	 * Vérification des paramètres pour la suppression d'une postit.
	 *
	 * @param postItId
	 * @return
	 */
	private List<String> checkDelete(final Integer postItId) {
		final List<String> errors = new ArrayList<>();

		if (postItId == null) {
			errors.add("Cette post-it n'existe pas, veuillez réessayer");
		}

		return errors;
	}

	/**
	 * Liaison liste de course
	 *
	 * @param listeId
	 * @param qty
	 * @param postItId
	 */
	private void linkListeDeCourse(final Integer listeId, final Integer qty, final Integer postItId) {
		if (listeId == null || qty == null || postItId == null) {
			return;
		}

		final Session session = listeDeCourseRepository.getSession();
		final Transaction transaction = session.beginTransaction();

		final ListeDeCourse liste = listeDeCourseRepository.findById(listeId, session);
		final PostIt postIt = postItRepository.findById(postItId, session);

		final ConcernerKey key = valueConcernerKey(listeId, postItId);
		final Concerner concerner = new Concerner();
		concerner.setKey(key);
		concerner.setQuantitePostIt(qty);

		postIt.getStockers().put(liste, concerner);
		postItRepository.update(postIt, session);

		transaction.commit();
	}

	/**
	 * Valorisation de la clé de Concerner.
	 *
	 * @param listeId
	 * @param postItId
	 * @return
	 */
	private ConcernerKey valueConcernerKey(final Integer listeId, final Integer postItId) {
		final ConcernerKey key = new ConcernerKey();
		key.setIdListeDeCourse(listeId);
		key.setIdPostIt(postItId);
		return key;
	}

}
