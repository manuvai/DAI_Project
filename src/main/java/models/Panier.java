package models;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Panier")
public class Panier {

	/**
	 * Identifiant du panier
	 */
	@Id
	@Column(name = "IdPanier")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPanier;

	/**
	 * Date de début de préparation du panier
	 */
	@Column(name = "DateDebutPreparation", nullable = true)
	private Date dateDebutPreparation;

	/**
	 * Date de fin de préparation du panier
	 */
	@Column(name = "DateFinPreparation", nullable = true)
	private Date dateFinPreparation;

	/**
	 * État du panier
	 */
	@Enumerated(EnumType.STRING)
	private Etat etat;

	/**
	 * Utilisateur propriétaire du panier
	 */
	@ManyToOne
	@JoinColumn(name = "UtilisateurId")
	private Utilisateur utilisateur;
	/**
	 * Créneau bloqué
	 */
	@ManyToOne
	@JoinColumn(name = "IdCreneau", nullable = true)
	private Creneau creneau;

	@MapKeyJoinColumn(name = "IdArticle")
	@OneToMany(mappedBy = "panierComposer", cascade = CascadeType.ALL)
	private Map<Article, Composer> composers;


	public Panier() {

	}

	public Panier(final Integer idPanier, final Date dateDebutPreparation, final Date dateFinPreparation, final Etat etat,
			final Utilisateur utilisateur, final Creneau creneau, final Map<Article, Composer> composers) {
		this.idPanier = idPanier;
		this.dateDebutPreparation = dateDebutPreparation;
		this.dateFinPreparation = dateFinPreparation;
		this.etat = etat;
		this.utilisateur = utilisateur;
		this.creneau = creneau;
		this.composers = composers;
	}

	/**
	 * Récupération de l'identifiant du panier.
	 *
	 * @return
	 */
	public Integer getId() {
		return idPanier;
	}

	/**
	 * Récupération de la date de début de préparation du panier.
	 *
	 * @return
	 */
	public Date getDateDebutPreparation() {
		return dateDebutPreparation;
	}

	/**
	 * MAJ de la date de début de préparation du panier.
	 *
	 * @param dateDebutPreparation
	 */
	public void setDateDebutPreparation(final Date dateDebutPreparation) {
		this.dateDebutPreparation = dateDebutPreparation;
	}

	/**
	 * Récupération de la date de fin de préparation du panier.
	 *
	 * @return
	 */
	public Date getDateFinPreparation() {
		return dateFinPreparation;
	}

	/**
	 * MAJ de la date de fin de préparation du panier.
	 *
	 * @param dateFinPreparation
	 */
	public void setDateFinPreparation(final Date dateFinPreparation) {
		this.dateFinPreparation = dateFinPreparation;
	}

	/**
	 * Récupération de l'état du panier.
	 *
	 * @return
	 */
	public Etat getEtat() {
		return etat;
	}

	/**
	 * MAJ de l'état du panier.
	 *
	 * @param etat
	 */
	public void setEtat(final Etat etat) {
		this.etat = etat;
	}

	/**
	 * Récupération du propriétaire du panier.
	 *
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * MAJ du propriétaire du panier.
	 *
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * Récupération du creneau
	 *
	 * @return the creneau
	 */
	public Creneau getCreneau() {
		return creneau;
	}

	/**
	 * MAJ du creneau
	 *
	 * @param creneau the creneau to set
	 */
	public void setCreneau(final Creneau creneau) {
		this.creneau = creneau;
	}

	/**
	 * Récupération du composers
	 *
	 * @return the composers
	 */
	public Map<Article, Composer> getComposers() {
		return composers;
	}

	/**
	 * MAJ des validers
	 *
	 * @param validers the validers to set
	 */
	public void setComposers(final Map<Article, Composer> composers) {
		this.composers = composers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creneau, dateDebutPreparation, dateFinPreparation, etat, idPanier, utilisateur, composers);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Panier other = (Panier) obj;
		return Objects.equals(creneau, other.creneau)
				&& Objects.equals(dateDebutPreparation, other.dateDebutPreparation)
				&& Objects.equals(dateFinPreparation, other.dateFinPreparation) && etat == other.etat
				&& Objects.equals(idPanier, other.idPanier) && Objects.equals(utilisateur, other.utilisateur)
				&& Objects.equals(composers, other.composers);
	}

	public enum Etat {
		ATTENTE, VALIDEE, LIVRE
	}

}
