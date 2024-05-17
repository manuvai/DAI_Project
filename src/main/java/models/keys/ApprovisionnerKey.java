package models.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clé de la relation entre commande et article.
 */
@Embeddable
public class ApprovisionnerKey implements Serializable {
	/**
	 * ID de serialization.
	 */
	private static final long serialVersionUID = -290185379034453541L;

	/**
	 * Identifiant de l'article
	 */
	@Column(name = "IdArticle")
	private int idArticle;

	/**
	 * Identifiant de la commande
	 */
	@Column(name = "IdCommande")
	private int idCommande;

	/**
	 * Constructeur vide
	 */
	public ApprovisionnerKey() {

	}

	/**
	 * Constructeur avec les attributs.
	 *
	 * @param idArticle
	 * @param idCommande
	 */
	public ApprovisionnerKey(final int idArticle, final int idCommande) {
		this.idArticle = idArticle;
		this.idCommande = idCommande;
	}

	/**
	 * Récupération de l'identifiant de l'article.
	 *
	 * @return
	 */
	public int getIdArticle() {
		return idArticle;
	}

	/**
	 * MAJ de l'identifiant de l'article.
	 *
	 * @param idArticle
	 */
	public void setIdArticle(final int idArticle) {
		this.idArticle = idArticle;
	}

	/**
	 * Récupération de l'identifiant de la commande.
	 *
	 * @return
	 */
	public int getIdCommande() {
		return idCommande;
	}

	/**
	 * MAJ de l'identifiant de la commande.
	 *
	 * @param idCommande
	 */
	public void setIdCommande(final int idCommande) {
		this.idCommande = idCommande;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idCommande);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ApprovisionnerKey other = (ApprovisionnerKey) obj;
		return idArticle == other.idArticle && idCommande == other.idCommande;
	}

}
