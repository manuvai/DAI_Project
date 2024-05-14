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
@Entity(name = "Categorie")
public class Categorie {
	/**
	 * id d'une categorie
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "Id")
	private Integer id;
	/**
	 * nom d'une categorie
	 */
	@Column(name = "NomCategorie")
	private String nomCategorie;
	
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "Id")  
	private Rayon rayon;

	
}
