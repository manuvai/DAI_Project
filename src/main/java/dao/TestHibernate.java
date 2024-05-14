package dao;


/**
 * Classe de test pour Hibernate.
 */
public class TestHibernate {
	/**
	 * Programme de test.
	 */
	public static void main(final String[] args) {
		HibernateUtil.getSessionFactory();

		System.out.println("Test Hibernate !");
		HibernateUtil.getSessionFactory();  
	}

}
