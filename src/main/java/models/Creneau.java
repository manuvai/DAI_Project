package models;

import java.util.Date;
import java.util.Objects;

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
import javax.persistence.OneToOne;
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
        _08h00_08h30,
        _08h30_09h00,
        _09h00_09h30,
        _09h30_10h00,
        _10h00_10h30,
        _10h30_11h00,
        _11h00_11h30,
        _11h30_12h00,
        _12h00_12h30,
        _14h30_15h00,
        _15h00_15h30,
        _15h30_16h00,
        _16h00_16h30,
        _16h30_17h00,
        _17h00_17h30,
        _17h30_18h00;
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
  
    
    //Constructeurs
    public Creneau() {}
    public Creneau(Date dateCreneau, HeureCreneau heureCreneau, int capacite) {
  		super();
  		this.dateCreneau = dateCreneau;
  		this.heureCreneau = heureCreneau;
  		this.capacite = capacite;
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
    public void setDateCreneau(Date dateCreneau) {
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
    public void setHeureCreneau(HeureCreneau heureCreneau) {
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
    public void setCapacite(int capacite) {
        this.capacite = capacite;
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
		return Objects.hash(capacite, idCreneau, dateCreneau, heureCreneau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creneau other = (Creneau) obj;
		return capacite == other.capacite && idCreneau == other.idCreneau
				&& Objects.equals(dateCreneau, other.dateCreneau) && heureCreneau == other.heureCreneau;
	}
}
   
