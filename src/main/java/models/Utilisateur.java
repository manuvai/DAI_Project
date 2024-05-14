package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nomUtilisateur")
	private String nom;

	@Column(name = "prenomUtilisateur")
	private String prenom;

	@Column(name = "adresseEmail")
	private String email;

	@Column(name = "motDePasse")
	private String motDePasse;

	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * Récupération de l'identifiant de l'utilisateur.
	 *
	 * @return
	 */
	public Integer getId() {
		return id;
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

	@Override
	public int hashCode() {
		return Objects.hash(email, id, motDePasse, nom, prenom, role);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final Utilisateur other = (Utilisateur) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(motDePasse, other.motDePasse) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom) && role == other.role;
	}

	enum Role {
		CLIENT, PREPARATEUR, GESTIONNAIRE
	}
}
