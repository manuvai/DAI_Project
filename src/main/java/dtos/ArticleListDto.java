package dtos;

import java.util.List;
import java.util.Objects;

import mappers.ArticleMapper;
import models.Article;

public class ArticleListDto {

	private List<Article> articles;

	public ArticleListDto() {

	}

	public ArticleListDto(final List<Article> inArticles) {
		articles = inArticles;
	}

	/**
	 * @return the articles
	 */
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(final List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		final XmlNode mainNode = new XmlNode("articles");

		if (articles != null) {
			articles.stream()
				.map(ArticleMapper.INSTANCE::articleToXmlNode)
				.forEach(mainNode::addNode);
		}

		return mainNode.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(articles);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ArticleListDto other = (ArticleListDto) obj;
		return Objects.equals(articles, other.articles);
	}

}
