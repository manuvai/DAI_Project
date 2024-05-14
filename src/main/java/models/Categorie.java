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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Classe représentant une categorie
 */
@Entity(name = "Categorie")
public class Categorie {
	/**
	 * id d'une categorie
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "IdCat")
	private Integer idCat;
	/**
	 * nom d'une categorie
	 */
	@Column(name = "NomCategorie")
	private String nomCategorie;
	
	

	/**
	 * sous categories
	 */
	@OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<SousCategorie> sousCategories = new HashSet(0);
	
	/**
	 * rayon de la categorie
	 */
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "IdRayon")  
	private Rayon rayon;
	

	public Categorie() {
	}
	
	public Categorie(Integer idCat, String nomCategorie, Rayon rayon) {
		super();
		this.idCat = idCat;
		this.nomCategorie = nomCategorie;
		this.rayon = rayon;
	}

	/**
	 * recupere l'id de categorie
	 * @return idCat
	 */
	public Integer getIdCat() {
		return idCat;
	}

	/**
	 * recupere le nom  de categorie
	 * @return nomCategorie
	 */
	public String getNomCategorie() {
		return nomCategorie;
	}

	/**
	 * recupere le rayon
	 * @return rayon
	 */
	public Rayon getRayon() {
		return rayon;
	}
	
	/**
	 * recupere la liste des sousCategories 
	 * @return sousCategories
	 */
	public Set<SousCategorie> getSousCategories() {
		return sousCategories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCat, nomCategorie, rayon, sousCategories);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(idCat, other.idCat) && Objects.equals(nomCategorie, other.nomCategorie)
				&& Objects.equals(rayon, other.rayon) && Objects.equals(sousCategories, other.sousCategories);
	}
	
	
	

	
}
