package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
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
    @Column(name="IdMagasin")
    private int idMagasin;

    /**
     * Le nom du magasin.
     *
     */
    @Column(name="NomMagasin")
    private String nomMagasin;
    
    /**
     * L'adresse du magasin.
     * 
     */
    @Column(name="AdresseMagasin")
    private String adresseMagasin;
    
    /**
     * Les horaires du magasin.
     * 
     */
    @Column(name="HorairesMagasin")
    private String horairesMagasin;

  
	@MapKeyJoinColumn(name = "IdArticle")
    @OneToMany(mappedBy = "magasinStock", cascade = CascadeType.ALL)
    private Map<Article,Stocker> stockers;

	/**
	 * Liste des commandes concernant l'approvisionnement du magasin.
	 */
	@OneToMany(mappedBy = "magasin", fetch = FetchType.LAZY)
	private Set<Commande> commandeApprovisionnement;

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

    public Magasin(String nomMagasin, String horairesMagasin, String adresseMagasin) {
        super();
        this.nomMagasin = nomMagasin;
        this.horairesMagasin= horairesMagasin;
        this.adresseMagasin=adresseMagasin;
        this.stockers = new HashMap<Article, Stocker>();
    }

    // Getters et setters

    /**
     * Obtient le code du magasin.
     *
     * @return Le code du magasin.
     */
    public int getCodeMagasin() {
        return idMagasin;
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
    @OneToMany(mappedBy = "magasin", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<Creneau> creneaux = new HashSet(0);

    /**
     * Définit le nom du magasin.
     *
     * @param nomMagasin Le nouveau nom du magasin.
     */
    public void setNomMagasin(final String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }
    
    /*
     * Obient les horaires du magasin
     * @return hoairesMagasin
     */
    public String getHorairesMagasin() {
  		return horairesMagasin;
  	}

    /*
     * Définit les horaires du magasin
     * @param horairesMagasin
     */
  	public void setHorairesMagasin(String horairesMagasin) {
  		this.horairesMagasin = horairesMagasin;
  	}

  	 public String getAdresseMagasin() {
 		return adresseMagasin;
 	}

 	public void setAdresseMagasin(String adresseMagasin) {
 		this.adresseMagasin = adresseMagasin;
 	}


	/**
	 * Récupération des stocks des articles.
	 *
	 * @return
	 */
	public Map<Article, Stocker> getStockers() {
		return stockers;
	}

	/**
	 * MAJ des stocks des articles.
	 *
	 * @param stockers
	 */
	public void setStockers(final Map<Article, Stocker> stockers) {
		this.stockers = stockers;
	}

	/**
	 * Récupération des commandes d'approvisionnement.
	 *
	 * @return
	 */
	public Set<Commande> getCommandeApprovisionnement() {
		return commandeApprovisionnement;
	}

	/**
	 * MAJ des commandes d'approvisionnement
	 *
	 * @param commandeApprovisionnement
	 */
	public void setCommandeApprovisionnement(final Set<Commande> commandeApprovisionnement) {
		this.commandeApprovisionnement = commandeApprovisionnement;
	}

	/**
	 * Récupération des créneaux
	 *
	 * @return
	 */
	public Set<Creneau> getCreneaux() {
		return creneaux;
	}

	/**
	 * MAJ des créneaux
	 *
	 * @param creneaux
	 */
	public void setCreneaux(final Set<Creneau> creneaux) {
		this.creneaux = creneaux;
	}

    // Méthodes
	/**
	 * Retourne une représentation sous forme de chaîne de caractères du magasin.
	 *
	 * @return Une chaîne de caractères représentant le magasin.
	 */
    @Override
    public String toString() {
        return "Magasin [codeMagasin=" + idMagasin + ", nomMagasin=" + nomMagasin + "]";
    }

    /**
     * Calcule le hashcode du magasin basé sur le code et le nom.
     *
     * @return Le hashcode du magasin.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMagasin, nomMagasin);
    }

    /**
     * Compare ce magasin avec un autre objet pour vérifier leur égalité.
     *
     * @param obj L'objet à comparer avec ce magasin.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
			return true;
		}
        if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
        final Magasin other = (Magasin) obj;
        return idMagasin == other.idMagasin && Objects.equals(nomMagasin, other.nomMagasin);
    }
}
