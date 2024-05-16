package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Article;

public class ArticleRepository extends AbstractRepository<Article, Integer> {

	public ArticleRepository() {
		super(Article.class);
	}
	
	public List<Article> getArticlesByRayonName(String nomRayon) {
		
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		List<Article> result = (List<Article>) session.createQuery("SELECT a FROM Article a , SousCategorie sc, Categorie c, Rayon r WHERE 		a.sousCategorie = sc.idSousCat AND sc.categorie = c.idCat AND c.rayon = r.id AND r.nomRayon = :nomRayon ").setParameter("nomRayon", 		nomRayon).list();
		
		session.close();
		return result;
	}

}
