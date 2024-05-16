package repositories;

import models.Article;

public class ArticleRepository extends AbstractRepository<Article, Integer> {

	public ArticleRepository() {
		super(Article.class);
	}
	
	

}
