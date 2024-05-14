package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * La classe Magasin représente un magasin avec un code et un nom.
 */
@Entity
@Table(name="Magasin")
public class Magasin {

    // Propriétés

    /**
     * Le code unique du magasin.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codeMagasin;

    /**
     * Le nom du magasin.
     */
    private String nomMagasin;

    // Constructeurs

    /**
     * Constructeur privé sans argument
     */
    private Magasin() {}

    /**
     * Constructeur pour créer un magasin avec un nom.
     *
     * @param nomMagasin Le nom du magasin.
     */
    public Magasin(String nomMagasin) {
        super();
        this.nomMagasin = nomMagasin;
    }

    // Getters et setters

    /**
     * Obtient le code du magasin.
     *
     * @return Le code du magasin.
     */
    public int getCodeMagasin() {
        return codeMagasin;
    }

    /**
     * Obtient le nom du magasin.
     *
     * @return Le nom du magasin.
     */
    public String getNomMagasin() {
        return nomMagasin;
    }

    //Mapping
    @OneToMany(mappedBy="magasin", fetch = FetchType.LAZY)
    private Set<Creneau> creneayx = new HashSet(0);
    /**
     * Définit le nom du magasin.
     *
     * @param nomMagasin Le nouveau nom du magasin.
     */
    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }

    // Méthodes

    /**
     * Retourne une représentation sous forme de chaîne de caractères du magasin.
     *
     * @return Une chaîne de caractères représentant le magasin.
     */
    @Override
    public String toString() {
        return "Magasin [codeMagasin=" + codeMagasin + ", nomMagasin=" + nomMagasin + "]";
    }

    /**
     * Calcule le hashcode du magasin basé sur le code et le nom.
     *
     * @return Le hashcode du magasin.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codeMagasin, nomMagasin);
    }

    /**
     * Compare ce magasin avec un autre objet pour vérifier leur égalité.
     *
     * @param obj L'objet à comparer avec ce magasin.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Magasin other = (Magasin) obj;
        return codeMagasin == other.codeMagasin && Objects.equals(nomMagasin, other.nomMagasin);
    }
}
