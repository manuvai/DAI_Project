package dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import models.Article;
import models.Categorie;
import models.Concerner;
import models.Rayon;
import models.SousCategorie;
import models.ListeDeCourse;
import models.Panier;
import models.PostIt;
import models.Recette;
import models.Utilisateur;
import models.ConcernerKey;
import models.Composer;
import models.Creneau;
import models.Magasin;
import models.Panier;
import models.Rayon;
import models.SousCategorie;
import models.Stocker;
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

			final List<Class<?>> classList = Arrays.asList(Rayon.class, Categorie.class, SousCategorie.class, Utilisateur.class, 
					Panier.class, Recette.class,
					PostIt.class, ListeDeCourse.class, Concerner.class, ConcernerKey.class,
					Magasin.class, Creneau.class, Article.class, Stocker.class, Composer.class);

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
