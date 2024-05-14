package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * La classe Recette concerne les recettes disponibles.
 *
 */
@Entity
@Table(name = "Recette")
public class Recette {

	/**
	 * Constructeur sans attribut
	 */
	public Recette() {
	}

	/**
	 * Constructeur avec tous les attributs
	 *
	 * @param idRecette
	 * @param postIts
	 */
	public Recette(final Integer idRecette, final Set<PostIt> postIts) {
		this.idRecette = idRecette;
		this.postIts = postIts;
	}

	/**
	 * Identifiant d'une recette
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdRecette")
	private Integer idRecette;

	/**
	 * Collection de post-its liés à une recette
	 */
	@ManyToMany
	@JoinTable(name = "Necessiter", joinColumns = @JoinColumn(name = "IdRecette"), inverseJoinColumns = @JoinColumn(name = "IdPostIt"))
	private Set<PostIt> postIts;

	/**
	 * Récupération des post-its liés
	 *
	 * @return the postIts
	 */
	public Set<PostIt> getPostIts() {
		return postIts;
	}

	/**
	 * MAJ des posts-its liés
	 *
	 * @param postIts the postIts to set
	 */
	public void setPostIts(final Set<PostIt> postIts) {
		this.postIts = postIts;
	}

}
