package models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ListeDeCourse")
public class ListeDeCourse {

	public ListeDeCourse() {
	}

	public ListeDeCourse(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idListDeCourse;

	/**
	 * rayon de la categorie
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IdUtilisateur")
	private Utilisateur utilisateur;

	/**
	 * getter for liste de course id
	 * @return id liste
	 */
	public Integer getIdListDeCourse() {
		return idListDeCourse;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idListDeCourse, utilisateur);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final ListeDeCourse other = (ListeDeCourse) obj;
		return Objects.equals(idListDeCourse, other.idListDeCourse) && Objects.equals(utilisateur, other.utilisateur);
	}


}
