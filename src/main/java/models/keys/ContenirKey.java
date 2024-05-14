package models.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContenirKey implements Serializable {

	/**
	 * serialVersionUid
	 */
	private static final long serialVersionUID = 3234691492746952558L;

	/**
	 * Identifiant d'article
	 */
	@Column(name = "IdArticle")
	private int idArticle;

	/**
	 * Identifiant de liste de course
	 */
	@Column(name = "IdListeCourse")
	private int idListeCourse;

	/**
	 * Constructeur de la clé sans les attributs
	 */
	public ContenirKey() {

	}

	/**
	 * Constructeur de la clé avec les attributs
	 *
	 * @param idArticle
	 * @param idListeCourse
	 */
	public ContenirKey(final int idArticle, final int idListeCourse) {
		this.idArticle = idArticle;
		this.idListeCourse = idListeCourse;
	}

	/**
	 * Récupération de l'identifiant de l'article.
	 *
	 * @return the idArticle
	 */
	public int getIdArticle() {
		return idArticle;
	}

	/**
	 * MAJ de l'identifiant de l'article.
	 *
	 * @param idArticle the idArticle to set
	 */
	public void setIdArticle(final int idArticle) {
		this.idArticle = idArticle;
	}

	/**
	 * Récupération de l'identifiant de la liste de course.
	 *
	 * @return the idListeCourse
	 */
	public int getIdListeCourse() {
		return idListeCourse;
	}

	/**
	 * MAJ de l'identifiant de la liste de course
	 *
	 * @param idListeCourse the idListeCourse to set
	 */
	public void setIdListeCourse(final int idListeCourse) {
		this.idListeCourse = idListeCourse;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idListeCourse);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ContenirKey other = (ContenirKey) obj;
		return idArticle == other.idArticle && idListeCourse == other.idListeCourse;
	}

}
