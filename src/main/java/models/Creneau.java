package models;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * La classe Creneau représente un créneau horaire avec une date, une heure et une capacité.
 */
@Entity
@Table(name = "Creneau")
public class Creneau {

	// Propriétés

	/**
	 * Le code unique du créneau.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdCreneau")
	private int idCreneau;

	/**
	 * La date du créneau.
	 */
	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name="DateCreneau")
	private Date dateCreneau;

	/**
	 * L'heure du créneau.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="HeureCreneau")
	private HeureCreneau heureCreneau;

	/**
	 * Enumération des créneaux horaires disponibles.
	 */
	public enum HeureCreneau {
		_08h00_08h30("08h00 à 08h30"),
		_08h30_09h00("08h30 à 09h00"),
		_09h00_09h30("09h00 à 09h30"),
		_09h30_10h00("09h30 à 10h00"),
		_10h00_10h30("10h00 à 10h30"),
		_10h30_11h00("10h30 à 11h00"),
		_11h00_11h30("11h00 à 11h30"),
		_11h30_12h00("11h30 à 12h00"),
		_12h00_12h30("12h00 à 12h30"),
		_14h30_15h00("14h30 à 15h00"),
		_15h00_15h30("15h00 à 15h30"),
		_15h30_16h00("15h30 à 16h00"),
		_16h00_16h30("16h00 à 16h30"),
		_16h30_17h00("16h30 à 17h00"),
		_17h00_17h30("17h00 à 17h30"),
		_17h30_18h00("17h30 à 18h00");

		private String value;

		HeureCreneau(final String inValue) {
			value = inValue;
		}

		public String getValue() {
			return value;
		}
	}


	/**
	 * La capacité du créneau.
	 */
	@Column(name="Capacite")
	private int capacite;


	//Mapping
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="IdMagasin")
	private Magasin magasin;

	@OneToMany(mappedBy = "creneau")
	private Set<Panier> paniers;

	//Constructeurs
	public Creneau() {}

	public Creneau(final Date dateCreneau, final HeureCreneau heureCreneau, final int capacite, final Magasin magasin,
			final Set<Panier> paniers) {
		this.dateCreneau = dateCreneau;
		this.heureCreneau = heureCreneau;
		this.capacite = capacite;
		this.magasin = magasin;
		this.paniers = paniers;

	}

	// Getters et setters

	/**
	 * Obtient le code du créneau.
	 *
	 * @return Le code du créneau.
	 */
	public int getCodeCreneau() {
		return idCreneau;
	}


	/**
	 * Obtient la date du créneau.
	 *
	 * @return La date du créneau.
	 */
	public Date getDateCreneau() {
		return dateCreneau;
	}

	/**
	 * Définit la date du créneau.
	 *
	 * @param dateCreneau La nouvelle date du créneau.
	 */
	public void setDateCreneau(final Date dateCreneau) {
		this.dateCreneau = dateCreneau;
	}

	/**
	 * Obtient l'heure du créneau.
	 *
	 * @return L'heure du créneau.
	 */
	public HeureCreneau getHeureCreneau() {
		return heureCreneau;
	}

	/**
	 * Définit l'heure du créneau.
	 *
	 * @param heureCreneau La nouvelle heure du créneau.
	 */
	public void setHeureCreneau(final HeureCreneau heureCreneau) {
		this.heureCreneau = heureCreneau;
	}

	/**
	 * Obtient la capacité du créneau.
	 *
	 * @return La capacité du créneau.
	 */
	public int getCapacite() {
		return capacite;
	}

	/**
	 * Définit la capacité du créneau.
	 *
	 * @param capacite La nouvelle capacité du créneau.
	 */
	public void setCapacite(final int capacite) {
		this.capacite = capacite;
	}

	/**
	 * Récupération du magasin
	 *
	 * @return the magasin
	 */
	public Magasin getMagasin() {
		return magasin;
	}

	/**
	 * MAJ du magasin
	 *
	 * @param magasin the magasin to set
	 */
	public void setMagasin(final Magasin magasin) {
		this.magasin = magasin;
	}

	/**
	 * Récupération des paniers
	 *
	 * @return the paniers
	 */
	public Set<Panier> getPaniers() {
		return paniers;
	}

	/**
	 * MAJ des paniers
	 *
	 * @param paniers the paniers to set
	 */
	public void setPaniers(final Set<Panier> paniers) {
		this.paniers = paniers;
	}

	// hashCode et equals
	/**
	 * Calcule le hashcode du créneau basé sur le code, la date, l'heure et la capacité.
	 *
	 * @return Le hashcode du créneau.
	 *
	 */
	@Override
	public int hashCode() {
		return Objects.hash(capacite, dateCreneau, heureCreneau, idCreneau, magasin);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Creneau other = (Creneau) obj;
		return capacite == other.capacite && Objects.equals(dateCreneau, other.dateCreneau)
				&& heureCreneau == other.heureCreneau && idCreneau == other.idCreneau
				&& Objects.equals(magasin, other.magasin);
	}

}

