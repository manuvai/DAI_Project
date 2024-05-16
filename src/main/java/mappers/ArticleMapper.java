package mappers;

import java.util.List;

import dtos.ArticleStockDto;
import models.Article;
import models.SousCategorie;

/**
 * Mapper pour transformer une collection de string en article
 */
public class ArticleMapper {

	public static final ArticleMapper INSTANCE = new ArticleMapper();

	/**
	 * Transformation d'une liste de valeur de colonne en Article
	 *
	 * @param colonnes
	 * @return
	 */
	public Article listToArticle(final List<String> colonnes) {
		Article article = null;

		if (colonnes != null && !colonnes.isEmpty()) {
			article = new Article();
			article.setDesc(colonnes.get(0));
			article.setNom(colonnes.get(1));
			article.setNutriscore(colonnes.get(2));
			article.setPoids(Integer.parseInt(colonnes.get(3)));
			article.setPrixUnitaire(Float.parseFloat(colonnes.get(4)));

			final SousCategorie sousCategorie = new SousCategorie();
			sousCategorie.setIdSousCat(Integer.parseInt(colonnes.get(5)));

			article.setSousCategorie(sousCategorie);
			article.setCheminImage(colonnes.get(6));
		}

		return article;
	}

	public ArticleStockDto articleToStockDto(final Article article) {
		ArticleStockDto dto = null;

		if (article != null) {
			dto = new ArticleStockDto(article.getId(), article.getNom(), article.getCheminImage());
		}

		return dto;
	}
}
