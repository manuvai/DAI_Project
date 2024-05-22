package repositories;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.HibernateUtil;

public abstract class AbstractRepository<T, K extends Serializable> {

	protected Class<T> clazz;

	protected AbstractRepository(final Class<T> inClazz) {
		clazz = inClazz;

	}

	/**
	 * Récupération d'une session.
	 *
	 * @return
	 */
	public Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	/**
	 * Exécution et récupération des résultats de la requête passée en paramètre.
	 *
	 * @param query
	 * @return
	 */
	public List<T> getQueryResults(final String query) {
		return getQueryResults(query, null);
	}

	/**
	 * Exécution et récupération des résultats de la requête passée en paramètre.
	 *
	 * @param inQuery
	 * @param mappedValues
	 * @return
	 */
	public List<T> getQueryResults(final String inQuery, final Map<String, Object> mappedValues) {
		final Session session = getSession();

		session.beginTransaction();

		final List<T> resultList = getQueryResults(inQuery, mappedValues, session);

		session.close();

		return resultList;
	}

	/**
	 * Exécution et récupération des résultats de la requête passée en paramètre.
	 *
	 * @param inQuery
	 * @param mappedValues
	 * @param session
	 * @return
	 */
	public List<T> getQueryResults(final String inQuery, final Map<String, Object> mappedValues,
			final Session session) {

		final Query<T> query = session.createQuery(inQuery);

		if (mappedValues != null) {
			mappedValues.forEach((key, value) -> query.setParameter(key, value));
		}

		return query.getResultList();
	}

	/**
	 * Récupération de toutes les entités
	 *
	 * @return
	 */
	public List<T> findAll() {

		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		final List<T> result = findAll(session);

		transaction.commit();

		return result;
	}

	/**
	 * Récupération de toutes les entités avec une session donnée
	 *
	 * @param session
	 * @return
	 */
	public List<T> findAll(final Session session) {

		final CriteriaQuery<T> cq = session.getCriteriaBuilder().createQuery(clazz);
		cq.from(clazz);

		return session.createQuery(cq).getResultList();
	}

	/**
	 * Récupération d'une entité à partir d'un identifiant donné
	 *
	 * @param id
	 * @return
	 */
	public T findById(final K id) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		final T result = findById(id, session);

		transaction.commit();

		return result;
	}

	/**
	 * Récupération d'une entité à partir d'un identifiant et une session donnée
	 *
	 * @param id
	 * @param session
	 * @return
	 */
	public T findById(final K id, final Session session) {
		T result = null;

		if (Objects.nonNull(id)) {

			result = session.get(clazz, id);
		}

		return result;
	}

	/**
	 * MAJ d'une entité
	 *
	 * @param entity
	 */
	public void update(final T entity) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		update(entity, session);

		transaction.commit();
	}

	/**
	 * MAJ d'une entité à partir d'une session donnée.
	 *
	 * @param entity
	 * @param session
	 */
	public void update(final T entity, final Session session) {
		session.update(entity);
	}

	/**
	 * Création d'une entité.
	 *
	 * @param entity
	 */
	public void create(final T entity) {
		createAll(Collections.singletonList(entity));
	}

	/**
	 * Création d'une liste d'entités.
	 *
	 * @param entities
	 */
	public void createAll(final List<T> entities) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		createAll(entities, session);

		transaction.commit();

	}

	/**
	 * Création d'une liste d'entités à partir d'une session donnée.
	 *
	 * @param entities
	 * @param session
	 */
	public void createAll(final List<T> entities, final Session session) {
		if (entities != null) {
			entities.forEach(session::save);
		}
	}

	/**
	 * Suppression d'une entité.
	 *
	 * @param entity
	 */
	public void delete(final T entity) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		delete(entity, session);

		transaction.commit();
	}

	/**
	 * Suppression d'une entité à partir d'une session donnée.
	 *
	 * @param entity
	 * @param session
	 */
	public void delete(final T entity, final Session session) {
		session.delete(entity);
	}

}