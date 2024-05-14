package models;

import dao.HibernateUtil;

public class Main {
	/**
	 * Programme de test.
	 */
	public static void main(final String[] args) {
		HibernateUtil.getSessionFactory();

		System.out.println("Test Hibernate!!!!!!!!!!!vcvvc !");
	}

}
