package dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import models.Panier;
import models.Utilisateur;


/**
 * Chargement de la configuration et création de la SessionFactory.
 * (hibernate.cfg.xml)
 */
public class HibernateUtil {
	private static final SessionFactory SESSION_FACTORY;

	static {
		try	{
			final Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			System.out.println("Hibernate Configuration loaded");

			// Liste des classes à charger. TODO Penser à mettre dans cette liste les classes des entités

			final List<Class<?>> classList = Arrays.asList(Utilisateur.class, Panier.class);

			classList.forEach(configuration::addAnnotatedClass);

			final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.build();
			System.out.println("Hibernate serviceRegistry created");

			SESSION_FACTORY = configuration.buildSessionFactory(serviceRegistry);

		} catch (final HibernateException ex) {
			/*----- Exception -----*/
			System.err.println("Initial SessionFactory creation failed.\n" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory () {
		return SESSION_FACTORY;
	}

} /*----- Fin de la classe HibernateUtil -----*/
