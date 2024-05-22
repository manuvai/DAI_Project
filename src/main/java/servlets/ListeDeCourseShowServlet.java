package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dtos.ListeCourseDto;
import mappers.ListeDeCourseMapper;
import models.Concerner;
import models.ListeDeCourse;
import models.PostIt;
import models.keys.ConcernerKey;
import repositories.ListeDeCourseRepository;
import repositories.PostItRepository;

/**
 * Servlet implementation class ListeDeCourseShow
 */
@WebServlet("/listes_courses/show")
public class ListeDeCourseShowServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	ListeDeCourseRepository listeDeCourseRepository = new ListeDeCourseRepository();
	PostItRepository postItRepository = new PostItRepository();
	ListeDeCourseMapper mapper = ListeDeCourseMapper.INSTANCE;

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Integer idListe = request.getParameter("id") == null
				? null
				: Integer.parseInt(request.getParameter("id"));

		if (idListe == null) {


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
		if (action == "delete") {
			processDelete(request, response);
		} else if ("add".equals(action)) {
			processAdd(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "listes_courses");

		}


	}

	private void processAdd(final HttpServletRequest request, final HttpServletResponse response) {

		final Integer listeId = Integer.parseInt(request.getParameter("id"));
		final String postItName = request.getParameter("postItName");
		final Integer qty = Integer.parseInt(request.getParameter("qty"));

		// TODO Check

		final PostIt postIt = new PostIt();
		postIt.setLabel(postItName);

		postItRepository.create(postIt);

		final Session session = listeDeCourseRepository.getSession();
		final Transaction transaction = session.beginTransaction();

		final ListeDeCourse liste = listeDeCourseRepository.findById(listeId, session);

		final Concerner concerner = new Concerner();
		concerner.setQuantitePostIt(qty);

		final ConcernerKey key = new ConcernerKey();
		key.setIdListeDeCourse(listeId);
		key.setIdPostIt(postIt.getIdPostIt());
		concerner.setKey(key);

		liste.getConcerners().put(postIt, concerner);

		listeDeCourseRepository.update(liste);

		transaction.commit();
	}

	private void processDelete(final HttpServletRequest request, final HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
