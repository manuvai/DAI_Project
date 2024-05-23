package dtos;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import models.Article;
import models.PostIt;

public class ListeCourseDto {

	private Integer id;

	private String nom;

	private Map<PostIt, Integer> postsItsMap = new HashMap<>();

	private Map<Article, Integer> articlesMap = new HashMap<>();

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}

	/**
	 * @return the postsItsMap
	 */
	public Map<PostIt, Integer> getPostsItsMap() {
		return postsItsMap;
	}

	/**
	 * @param postsItsMap the postsItsMap to set
	 */
	public void setPostsItsMap(final Map<PostIt, Integer> postsItsMap) {
		this.postsItsMap = postsItsMap;
	}


	public int getNbElements() {
		int result = 0;

		for (final Entry<PostIt, Integer> entry : getPostsItsMap().entrySet()) {
			result += entry.getValue();
		}

		for (final Entry<Article, Integer> entry : getArticlesMap().entrySet()) {
			result += entry.getValue();
		}

		return result;
	}

	/**
	 * @return the articlesMap
	 */
	public Map<Article, Integer> getArticlesMap() {
		return articlesMap;
	}

	/**
	 * @param articlesMap the articlesMap to set
	 */
	public void setArticlesMap(final Map<Article, Integer> articlesMap) {
		this.articlesMap = articlesMap;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ListeCourseDto other = (ListeCourseDto) obj;
		return id == other.id && Objects.equals(nom, other.nom);
	}

}
