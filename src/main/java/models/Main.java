package models;

import dao.HibernateUtil;

public class Main {
	public static void main (final String[] args) {
		System.out.println("Test Hibernate !!!!");
		HibernateUtil.getSessionFactory();  
	}
}
