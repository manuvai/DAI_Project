package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe représentant une categorie
 */
@Entity(name = "SousCategorie")
public class SousCategorie {
	/**
	 * id d'une sous-categorie
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "IdSousCat")
	private Integer idSousCat;
	/**
	 * nom d'une sous-categorie
	 */
	@Column(name = "NomSousCategorie")
	private String nomSousCategorie;
	
	/**
	 * categorie de la sous-categorie
	 */
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "IdCat")  
	private Categorie categorie;

	public Integer getIdSousCat() {
		return idSousCat;
	}

	public String getNomSousCategorie() {
		return nomSousCategorie;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categorie, idSousCat, nomSousCategorie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SousCategorie other = (SousCategorie) obj;
		return Objects.equals(categorie, other.categorie) && Objects.equals(idSousCat, other.idSousCat)
				&& Objects.equals(nomSousCategorie, other.nomSousCategorie);
	}
	
	

}
