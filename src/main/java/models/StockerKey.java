package models;

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

    @Column(name = "IdCreneau")
    private int idService;

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
    public StockerKey(int idMagasin, int idService) {
        super();
        this.idMagasin = idMagasin;
        this.idService = idService;
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

    /**
     * Obtient l'identifiant du service.
     *
     * @return l'IdService.
     */
    public int getIdService() {
        return idService;
    }

    /**
     * Définit l'identifiant du service.
     *
     * @param idService le nouvel IdService.
     */
    public void setIdService(int idService) {
        idService = idService;
    }

    /**
     * Calcule un code de hachage pour cette StockerKey.
     *
     * @return une valeur de code de hachage pour cet objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMagasin, idService);
    }

    /**
     * Indique si un autre objet est "égal à" celui-ci.
     *
     * @param obj l'objet de référence avec lequel comparer.
     * @return true si cet objet est le même que l'argument obj ; false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StockerKey other = (StockerKey) obj;
        return idMagasin == other.idMagasin && idService == other.idService;
    }
}
