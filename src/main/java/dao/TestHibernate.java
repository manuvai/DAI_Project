package dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

import models.Utilisateur;

/**
 * Classe de test pour Hibernate.
 */
public class TestHibernate {
	/**
	 * Programme de test.
	 */
	public static void main(final String[] args) {
		HibernateUtil.getSessionFactory();

		System.out.println("Test Hibernate!");

		HibernateUtil.getSessionFactory();

		System.out.println("Test Hibernate!");

		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		final CriteriaQuery<Utilisateur> cq = session.getCriteriaBuilder().createQuery(Utilisateur.class);
		cq.from(Utilisateur.class);

		final List<Utilisateur> liste = session.createQuery(cq).getResultList();
		System.out.println("Test Hibernate !");

	}


}
