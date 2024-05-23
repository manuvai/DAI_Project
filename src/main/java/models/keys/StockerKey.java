package models.keys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

/**
 * La classe StockerKey représente une clé composite pour l'entité Stocker,
 * composée de IdMagasin et IdService.
 */
public class StockerKey implements Serializable {

    @Column(name = "IdMagasin")
    private int idMagasin;

    @Column(name = "IdArticle")
    private int idArticle;

    /**
     * Constructeur par défaut de StockerKey.
     * Initialise une nouvelle instance de la classe StockerKey.
     */
    public StockerKey() {}

    /**
     * Initialise une nouvelle instance de la classe StockerKey avec les IdMagasin et IdService spécifiés.
     *
     * @param idMagasin l'identifiant du magasin.
     * @param idService l'identifiant du service.
     */
    public StockerKey(int idMagasin, int idArticle) {
        super();
        this.idMagasin = idMagasin;
        this.idArticle = idArticle;
    }

    /**
     * Obtient l'identifiant du magasin.
     *
     * @return l'IdMagasin.
     */
    public int getIdMagasin() {
        return idMagasin;
    }

    /**
     * Définit l'identifiant du magasin.
     *
     * @param idMagasin le nouvel IdMagasin.
     */
    public void setIdMagasin(int idMagasin) {
        idMagasin = idMagasin;
    }

	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idMagasin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockerKey other = (StockerKey) obj;
		return idArticle == other.idArticle && idMagasin == other.idMagasin;
	}

	public int getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

    

    
}
