package repositories;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Utilisateur;
import models.Article.Nutriscore;

public class UtilisateurRepository extends AbstractRepository<Utilisateur, Integer> {

	public UtilisateurRepository() {
		super(Utilisateur.class);
	}
	
	public boolean findMail(final String mail) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		Utilisateur utilisateur =  (Utilisateur) session.createQuery("from Utilisateur where AdresseEmail = :mail").setParameter("mail", mail).uniqueResult();

		session.close();
		return utilisateur != null;
	}
	
	public Utilisateur findUserByMail(final String mail) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		Utilisateur utilisateur =  (Utilisateur) session.createQuery("from Utilisateur where AdresseEmail = :mail").setParameter("mail", mail).uniqueResult();

		session.close();
		return utilisateur;
	}
	
	public long findNombreArticlesDiffCommandesUser(final int id) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		String queryString  = "SELECT count(*) "
		 		+ "FROM Utilisateur u , Panier p, Composer c "
		 		+ "WHERE u = p.utilisateur and p = c.panierComposer "
					+ "and u.idUtilisateur = :id";
		 
		 Query query = session.createQuery(queryString);
		 query.setParameter("id", id);
		 
		 Long nbrArticle =  (Long) query.uniqueResult();

		session.close();
		return nbrArticle;
	}
	
	public long findNombreArticlesBIODiffCommandesUser(final int id) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		 String queryString  = "SELECT count(*) "
			 		+ "FROM Utilisateur u , Panier p, Composer c, Article a  "
			 		+ "WHERE u = p.utilisateur and p = c.panierComposer and c.articleComposer = a  "
						+ "and u.idUtilisateur = :id  "
						+ "and a.bio = true";
		 
		 Query query = session.createQuery(queryString);
		 query.setParameter("id", id);
		 
		 Long nbrArticle =  (Long) query.uniqueResult();

		session.close();
		return nbrArticle;
	}
	
	public long findNombreArticlesNutriscoreDiffCommandesUser(final int id, String nutri) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		 String queryString  = "SELECT count(*) "
			 		+ "FROM Utilisateur u , Panier p, Composer c, Article a  "
			 		+ "WHERE u = p.utilisateur and p = c.panierComposer and c.articleComposer = a  "
						+ "and u.idUtilisateur = :id  "
						+ "and a.nutriscore = :nutri";
		 
		 Query query = session.createQuery(queryString);
		 query.setParameter("id", id);
		 query.setParameter("nutri", nutri);
		 
		 Long nbrArticle =  (Long) query.uniqueResult();

		session.close();
		return nbrArticle;
	}
	
	public long findNombreArticlesCategorieUser(final int id, String categorie) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		 String queryString  = "SELECT count(*) "
			 		+ "FROM Utilisateur u , Panier p, Composer c, Article a, Categorie ca, SousCategorie s "
			 		+ "WHERE u = p.utilisateur and p = c.panierComposer and c.articleComposer = a  "
			 		+ "and a.sousCategorie = s and s.categorie = ca "
						+ "and u.idUtilisateur = :id  "
						+ "and ca.nomCategorie = :categorie";
		 
		 Query query = session.createQuery(queryString);
		 query.setParameter("id", id);
		 query.setParameter("categorie", categorie);
		 
		 Long nbrArticle =  (Long) query.uniqueResult();

		session.close();
		return nbrArticle;
	}
	
	

}
