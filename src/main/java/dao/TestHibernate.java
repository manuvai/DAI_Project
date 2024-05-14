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
<<<<<<< HEAD
		HibernateUtil.getSessionFactory();

		System.out.println("Test Hibernate!");
=======
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		final CriteriaQuery<Utilisateur> cq = session.getCriteriaBuilder().createQuery(Utilisateur.class);
		cq.from(Utilisateur.class);

		final List<Utilisateur> liste = session.createQuery(cq).getResultList();
		System.out.println("Test Hibernate !");
>>>>>>> d9ab2c37d163a4b380d25a6602088cd86121c7e3
	}


}
