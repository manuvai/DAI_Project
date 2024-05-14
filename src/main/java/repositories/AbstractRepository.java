package repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.HibernateUtil;

public abstract class AbstractRepository<T, K extends Serializable> {

	protected Class<T> clazz;

	protected AbstractRepository(final Class<T> inClazz) {
		clazz = inClazz;

	}

	public List<T> findAll() {

		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		final List<T> result = findAll(session);

		transaction.commit();

		return result;
	}

	public List<T> findAll(final Session session) {

		final CriteriaQuery<T> cq = session.getCriteriaBuilder().createQuery(clazz);
		cq.from(clazz);

		return session.createQuery(cq).getResultList();
	}

	public T findById(final K id) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		final T result = findById(id, session);

		transaction.commit();

		return result;
	}

	public T findById(final K id, final Session session) {
		T result = null;

		if (Objects.nonNull(id)) {

			result = session.get(clazz, id);
		}

		return result;
	}

	public void update(final T entity) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		update(entity, session);

		transaction.commit();
	}

	public void update(final T entity, final Session session) {
		session.update(entity);
	}

	public void create(final T entity) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		create(entity, session);

		transaction.commit();
	}

	public void create(final T entity, final Session session) {
		session.save(entity);
	}

	public void delete(final T entity) {
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		delete(entity, session);

		transaction.commit();
	}

	public void delete(final T entity, final Session session) {
		session.delete(entity);
	}

	protected Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

}
