package repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import models.ListeDeCourse;

public class ListeDeCourseRepository extends AbstractRepository<ListeDeCourse, Integer> {

	public ListeDeCourseRepository() {
		super(ListeDeCourse.class);
	}

	/**
	 * Récupération des listes de courses d'un utilisateur donné.
	 *
	 * @param id
	 * @return
	 */
	public List<ListeDeCourse> findByUtilisateurId(final Integer id) {

		List<ListeDeCourse> resultList = new ArrayList<>();
		if (id != null) {
			final Session session = getSession();
			session.beginTransaction();

			resultList = findByUtilisateurId(id, session);

			session.close();
		}

		return resultList;

	}

	/**
	 * Récupération des listes de courses d'un utilisateur donné.
	 *
	 * @param id
	 * @param session
	 * @return
	 */
	public List<ListeDeCourse> findByUtilisateurId(final Integer id, final Session session) {

		List<ListeDeCourse> resultList = new ArrayList<>();
		if (id != null) {
			final String query = "SELECT l " + "FROM ListeDeCourse l "
					+ "WHERE l.utilisateur.idUtilisateur = :idUtilisateur";

			final Map<String, Object> mappedValues = Collections.singletonMap("idUtilisateur", id);
			resultList = getQueryResults(query, mappedValues, session);
		}

		return resultList;

	}

}
