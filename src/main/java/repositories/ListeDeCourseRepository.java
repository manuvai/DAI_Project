package repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Composer;
import models.Concerner;
import models.ListeDeCourse;
import models.PostIt;

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
	
	@SuppressWarnings({ "unchecked", "resource" })
	public void addListeCourseToUtilisateurId(Integer utilisateurId, ListeDeCourse ldc) {
		
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		
		final List<Concerner> concerners = session.createQuery("FROM Concerner c WHERE c.liste.idListDeCourse = :ldcId")
                .setParameter("ldcId", ldc.getIdListDeCourse())
                .getResultList();
		
		//On crée la liste de courses avec le nom de la recette
		session.createSQLQuery("INSERT INTO ListeDeCourse (IdUtilisateur, NomListeDeCourse) "
							 + "VALUES (:IdUtilisateur, :NomListeDeCourse)")
				.setParameter("IdUtilisateur", utilisateurId)
				.setParameter("NomListeDeCourse", ldc.getNom())
				.executeUpdate();
		
		transaction.commit();
		
		
		session = getSession();
		transaction = session.beginTransaction(); 
		//On récupère l'id de la ListeDeCourses qu'on vient de créer
		final List<Integer> idListeDeCourses = (List<Integer>) session.createQuery("SELECT MAX(idListDeCourse) "
																   + "FROM ListeDeCourse "
																   + "WHERE IdUtilisateur = :IdUtilisateur").setParameter("IdUtilisateur", utilisateurId).getResultList();
		
		
		
		for (Concerner concerner : concerners) {
			
			session.createSQLQuery("INSERT INTO PostIt (Label) "
				     + "VALUES (:Label)")
							.setParameter("Label", concerner.getPostit().getLabel()).executeUpdate();
			
			transaction.commit();
			
			session = getSession();
			transaction = session.beginTransaction(); 
			
			List<PostIt> postIt = (List<PostIt>) session.createQuery("SELECT MAX(idPostIt) "
					   + "FROM PostIt ").getResultList();
			
			session.createSQLQuery("INSERT INTO Concerner (IdListeDeCourse, IdPostIt, Quantite) "
							     + "VALUES (:IdListeDeCourse, :IdPostIt, :Quantite)")
										.setParameter("IdListeDeCourse", idListeDeCourses.get(idListeDeCourses.size() -1))
										.setParameter("IdPostIt", postIt.get(postIt.size() - 1).getIdPostIt())
										.setParameter("Quantite", concerner.getQuantitePostIt()).executeUpdate();
		}
		
		transaction.commit();

	}
}
