package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
    public ComposerKey(int idArticle, int idPanier) {
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
    public void setIdArticle(int idArticle) {
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
    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idArticle;
		result = prime * result + idPanier;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComposerKey other = (ComposerKey) obj;
		if (idArticle != other.idArticle)
			return false;
		if (idPanier != other.idPanier)
			return false;
		return true;
	}
}