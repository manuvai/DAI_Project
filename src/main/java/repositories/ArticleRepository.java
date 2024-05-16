package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Article;

public class ArticleRepository extends AbstractRepository<Article, Integer> {

	public ArticleRepository() {
		super(Article.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findArticlePanier(Integer panierId) {

			final Session session = getSession();
			final Transaction transaction = session.beginTransaction();

			final List<Article> articles = (List<Article>) session.createQuery("SELECT a "
																			 + "FROM Article a, Composer c, Panier p "
																			 + "WHERE a.idArticle = c.articleComposer "
																			 + "AND c.panierComposer = p.idPanier "
																			 + "AND p.idPanier = :panierId").setParameter("panierId", panierId.toString()).list();
			
			System.out.println("-------------------------------------------------");
			System.out.println(articles.size());
			System.out.println("-------------------------------------------------");

		
			transaction.commit();

			return articles;
		}
	
	

}
