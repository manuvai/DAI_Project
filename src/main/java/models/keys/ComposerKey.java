package models.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ComposerKey, clé pour Composer
 */
@SuppressWarnings("serial")
@Embeddable
public class ComposerKey implements Serializable {
	/**
	 * Identifiant de l'article
	 */
    @Column(name = "idArticle")
    private int idArticle;

    /**
     * Identifiant du panier
     */
    @Column(name = "idPanier")
    private int idPanier;

    /**
     * Constructeur vide
     */
    public ComposerKey() {}

    /**
     * Constructeur ComposerKey
     * @param idArticle identifiant de l'article
     * @param idPanier identifiant du panier
     */
    public ComposerKey(final int idArticle, final int idPanier) {
        setIdArticle(idArticle);
        setIdPanier(idPanier);
    }

    /**
     * Getter idArticle
     * @return idArticle
     */
    public int getIdArticle() {
        return idArticle;
    }

    /**
     * Setter idArticle
     * @param idArticle
     */
    public void setIdArticle(final int idArticle) {
        this.idArticle = idArticle;
    }

    /**
     * Getter idPanier
     * @return idPanier
     */
    public int getIdPanier() {
        return idPanier;
    }

    /**
     * Setter idPanier
     * @param idPanier
     */
    public void setIdPanier(final int idPanier) {
        this.idPanier = idPanier;
    }

	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idPanier);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final ComposerKey other = (ComposerKey) obj;
		return idArticle == other.idArticle && idPanier == other.idPanier;
	}

}