package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Utilisateur")
public class Utilisateur {

	@Id
	@Column(name = "IdUtilisateur")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUtilisateur;

	@Column(name = "NomUtilisateur")
	private String nom;

	@Column(name = "PrenomUtilisateur")
	private String prenom;

	@Column(name = "AdresseEmail")
	private String email;

	@Column(name = "MotDePasse")
	private String motDePasse;

	@OneToMany(mappedBy = "utilisateur")
	private Set<Panier> paniers;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<ListeDeCourse> listeDeCourse = new HashSet(0);

	
	public Utilisateur(String nom, String prenom, String email, String motDePasse, Role role) {
		super();
		this.idUtilisateur = idUtilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.paniers = new HashSet();
		this.role = role;
		this.listeDeCourse = new HashSet();
	}
	public Utilisateur() {};

	/**
	 * Récupération de l'identifiant de l'utilisateur.
	 *
	 * @return
	 */
	public Integer getId() {
		return idUtilisateur;
	}

	/**
	 * Récupération du nom de l'utilisateur.
	 *
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * MAJ du nom de l'utilisateur.
	 *
	 * @param nom
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}

	/**
	 * Récupération du prenom de l'utilisateur.
	 *
	 * @return
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * MAJ du prenom de l'utilisateur.
	 *
	 * @param prenom
	 */
	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Récupération de l'email de l'utilisateur.
	 *
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * MAJ de l'email de l'utilisateur.
	 *
	 * @param email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Récupération du mot de passe de l'utilisateur.
	 *
	 * @return
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * MAJ du mot de passe de l'utilisateur.
	 *
	 * @param motDePasse
	 */
	public void setMotDePasse(final String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * Récupération du rôle de l'utilisateur.
	 *
	 * @return
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * MAJ du rôle de l'utilisateur.
	 *
	 * @param role
	 */
	public void setRole(final Role role) {
		this.role = role;
	}

	/**
	 * Récupération des paniers de l'utilisateur.
	 *
	 * @return
	 */
	public Set<Panier> getPaniers() {
		return paniers;
	}

	public Set<ListeDeCourse> getListeDeCourse() {
		return listeDeCourse;
	}

	public void addListeDeCourse(final ListeDeCourse listeDeCourse) {
		this.listeDeCourse.add(listeDeCourse);
	}

	/**
	 * MAJ des paniers de l'utilisateur.
	 *
	 * @param paniers the paniers to set
	 */
	public void setPaniers(final Set<Panier> paniers) {
		this.paniers = paniers;
	}


	@Override
	public int hashCode() {
		return Objects.hash(email, idUtilisateur, motDePasse, nom, prenom, role);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Utilisateur other = (Utilisateur) obj;
		return Objects.equals(email, other.email) && Objects.equals(idUtilisateur, other.idUtilisateur)
				&& Objects.equals(motDePasse, other.motDePasse) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom) && role == other.role;
	}



	public enum Role {
		CLIENT, PREPARATEUR, GESTIONNAIRE
	}
}
