package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.keys.ContenirKey;

@Entity
@Table(name = "Contenir")
public class Contenir {

	@EmbeddedId
	private ContenirKey key;

	@Column(name = "Qte")
	private Integer qte;

	@ManyToOne
	@JoinColumn(name = "IdArticle", insertable = false, updatable = false)
	private Article article;

	@ManyToOne
	@JoinColumn(name = "IdListeCourse", insertable = false, updatable = false)
	private ListeDeCourse listeCourse;

	/**
	 * Récupération de la clé
	 *
	 * @return the key
	 */
	public ContenirKey getKey() {
		return key;
	}

	/**
	 * MAJ de la clé
	 *
	 * @param key the key to set
	 */
	public void setKey(final ContenirKey key) {
		this.key = key;
	}

	/**
	 * Récupération de la quantité
	 *
	 * @return the qte
	 */
	public Integer getQte() {
		return qte;
	}

	/**
	 * MAJ de la quantité
	 *
	 * @param qte the qte to set
	 */
	public void setQte(final Integer qte) {
		this.qte = qte;
	}

	/**
	 * Récupération de l'article
	 *
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * MAJ de l'article
	 *
	 * @param article the article to set
	 */
	public void setArticle(final Article article) {
		this.article = article;
	}

	/**
	 * Récupération de la liste de courses
	 *
	 * @return the listeCourse
	 */
	public ListeDeCourse getListeCourse() {
		return listeCourse;
	}

	/**
	 * MAJ de la liste de courses
	 *
	 * @param listeCourse the listeCourse to set
	 */
	public void setListeCourse(final ListeDeCourse listeCourse) {
		this.listeCourse = listeCourse;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Contenir other = (Contenir) obj;
		return Objects.equals(key, other.key);
	}

}
