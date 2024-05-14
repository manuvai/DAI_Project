package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import models.Article;
import models.Categorie;
import models.Composer;
import models.Concerner;
import models.Contenir;
import models.Creneau;
import models.ListeDeCourse;
import models.Magasin;
import models.Panier;
import models.PostIt;
import models.Rayon;
import models.Recette;
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

			final List<Class<?>> classList = new ArrayList<>();
			classList.add(Rayon.class);
			classList.add(Categorie.class);
			classList.add(SousCategorie.class);
			classList.add(Utilisateur.class);
			classList.add(Panier.class);
			classList.add(Recette.class);
			classList.add(PostIt.class);
			classList.add(ListeDeCourse.class);
			classList.add(Concerner.class);
			classList.add(Magasin.class);
			classList.add(Creneau.class);
			classList.add(Article.class);
			classList.add(Stocker.class);
			classList.add(Composer.class);
			classList.add(Contenir.class);

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
