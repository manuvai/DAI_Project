package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Classe représentant un rayon
 */
@Entity(name = "Rayon")
public class Rayon {
	/**
	 * id d'un rayon
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "IdRayon")
	private Integer id;
	/**
	 * nom d'un rayon
	 */
	@Column(name = "NomRayon")
	private String nomRayon;
	
	
	/**
	 * chemin image d'un rayon
	 */
	@Column(name = "CheminImageRayon")
	private String cheminImageRayon;
	
	
	@OneToMany(mappedBy = "rayon", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<Categorie> categories = new HashSet(0);

	/**
	 * constructeur vide du rayon
	 */
	public Rayon() {}
	/**
	 * @param id
	 * @param nom du rayon
	 * constructeur d'un rayon
	 */
	public Rayon(Integer id, String nomRayon) {
		super();
		this.id = id;
		this.nomRayon = nomRayon;
	}
	/**
	 * Recupérer les categories
	 * @return categories
	 */
	public Set<Categorie> getCategories() {
		return categories;
	}
	/**
	 * ajouter une categorie au rayon
	 * @param categorie
	 */
	public void addCategories(Categorie categorie) {
		this.categories.add(categorie);
	}
	/**
	 * Recupérer Id d'un rayon
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Recupérer le nom d'un rayon
	 * @return nom
	 */
	public String getNomRayon() {
		return nomRayon;
	}
	
	
	/**
	 * Returns the image path of the rayon.
	 *
	 * @return the image path of the rayon
	 */
	public String getCheminImageRayon() {
	    return cheminImageRayon;
	}

	/**
	 * Sets the image path of the rayon.
	 *
	 * @param cheminImageRayon the new image path of the rayon
	 */
	public void setCheminImageRayon(String cheminImageRayon) {
	    this.cheminImageRayon = cheminImageRayon;
	}

	/**
	 * hashcode du rayon
	 * @return la valeur du hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, nomRayon);
	}
	/**
	 * equals du rayon
	 * @param obj à tester
	 * @return si les obj sont égaux
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rayon other = (Rayon) obj;
		return Objects.equals(id, other.id) && Objects.equals(nomRayon, other.nomRayon);
	}
	
	

}
