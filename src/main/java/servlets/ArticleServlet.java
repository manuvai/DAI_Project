package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Article;
import models.Contenir;
import models.ListeDeCourse;
import models.Utilisateur;
import models.keys.ContenirKey;
import repositories.ArticleRepository;
import repositories.ContenirRepository;
import repositories.ListeDeCourseRepository;

/**
 * Servlet implementation class Article
 */
@WebServlet("/Article")
public class ArticleServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	ListeDeCourseRepository listeDeCourseRepository = new ListeDeCourseRepository();
	ArticleRepository articleRepository = new ArticleRepository();
	ContenirRepository contenirRepository = new ContenirRepository();


	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final Integer articleId = Integer.parseInt(request.getParameter("idArticle"));

		final Session session = articleRepository.getSession();
		session.beginTransaction();

		final Article article = articleRepository.findById(articleId, session);

		request.setAttribute("article", article);

		final Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");

		if (utilisateur != null) {
			Hibernate.initialize(article.getContenirs());
			final List<ListeDeCourse> listes = listeDeCourseRepository.findByUtilisateurId(
					utilisateur.getId(),
					session);
			request.setAttribute("listesDeCourse", listes);
		}

		session.close();
		view("article", request, response);

	}


	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String action = request.getParameter("action");

		if ("editListeDeCourse".equals(action)) {
			processEditListeDeCourse(request, response);
			return;

		}

		doGet(request, response);

	}

	/**
	 * Gestion de l'ajout de l'article à une liste de courses
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processEditListeDeCourse(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String listeIdString = request.getParameter("listeDeCourse-id");
		final String articleIdString = request.getParameter("article-id");
		final String qtyString = request.getParameter("qty");

		final Integer listeId = listeIdString == null || listeIdString.isBlank()
				? null
				: Integer.parseInt(listeIdString);

		final Integer articleId = articleIdString == null || articleIdString.isBlank()
				? null
				: Integer.parseInt(articleIdString);

		final Integer qty = qtyString == null || qtyString.isBlank()
				? null
				: Integer.parseInt(qtyString);

		if (articleId == null) {
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}

		if (listeId == null) {
			doGet(request, response);
			return;
		}

		if (qty == null) {
			ajouterErreur("Vous devez fournir une quantité pour la liste", request);
			doGet(request, response);
			return;
		}

		final Session session = articleRepository.getSession();
		final Transaction transaction = session.beginTransaction();

		final ListeDeCourse liste = listeDeCourseRepository.findById(listeId, session);
		final Article article = articleRepository.findById(articleId, session);

		final ContenirKey key = new ContenirKey(article.getId(), liste.getIdListDeCourse());
		final Contenir contenir = new Contenir();
		contenir.setKey(key);

		String message;
		if (qty <= 0) {
			message = "L'article a bien été retiré de la liste";
			article.getContenirs().remove(liste);
			contenirRepository.delete(contenir, session);

		} else {
			message = "L'article a bien été ajouté à la liste";
			contenir.setQte(qty);
			article.getContenirs().put(liste, contenir);
		}

		articleRepository.update(article, session);

		transaction.commit();

		ajouterSucces(message, request);
		responseGet(request, response);

	}

}
