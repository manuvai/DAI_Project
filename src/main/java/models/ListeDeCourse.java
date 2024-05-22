package models;

import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ListeDeCourse")
public class ListeDeCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idListDeCourse;

	@Column(name = "NomListeDeCourse")
	private String nom;

	/**
	 * rayon de la categorie
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IdUtilisateur")
	private Utilisateur utilisateur;

	@MapKeyJoinColumn(name = "IdPostIt")
	@OneToMany(mappedBy = "liste", cascade = CascadeType.ALL)
	private Map<PostIt, Concerner> concerners;

	public ListeDeCourse() {
	}

	public ListeDeCourse(final String nom) {
		this(nom, null);
	}

	public ListeDeCourse(final Utilisateur utilisateur) {
		this(null, utilisateur);
	}

	public ListeDeCourse(final String inNom, final Utilisateur inUtilisateur) {
		nom = inNom;
		utilisateur = inUtilisateur;
	}

	/**
	 * @return the idListDeCourse
	 */
	public Integer getIdListDeCourse() {
		return idListDeCourse;
	}

	/**
	 * @param idListDeCourse the idListDeCourse to set
	 */
	public void setIdListDeCourse(final Integer idListDeCourse) {
		this.idListDeCourse = idListDeCourse;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}

	/**
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return the concerners
	 */
	public Map<PostIt, Concerner> getConcerners() {
		return concerners;
	}

	/**
	 * @param concerners the concerners to set
	 */
	public void setConcerners(final Map<PostIt, Concerner> concerners) {
		this.concerners = concerners;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idListDeCourse, nom, utilisateur);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ListeDeCourse other = (ListeDeCourse) obj;
		return Objects.equals(idListDeCourse, other.idListDeCourse)
				&& Objects.equals(nom, other.nom) && Objects.equals(utilisateur, other.utilisateur);
	}


}
