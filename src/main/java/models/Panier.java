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
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Panier")
public class Panier {

	@Id
	@Column(name = "IdPanier")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPanier;

	@Column(nullable = true)
	private Date dateDebutPreparation;

	@Column(nullable = true)
	private Date dateFinPreparation;

	@Enumerated(EnumType.STRING)
	private Etat etat;

    @MapKeyJoinColumn(name = "IdArticle")
    @OneToMany(mappedBy = "articleValidateur", cascade = CascadeType.ALL)
    private Map<Panier,Composer> validers;

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

	@Override
	public int hashCode() {
		return Objects.hash(dateDebutPreparation, dateFinPreparation, etat, idPanier);
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
		return Objects.equals(dateDebutPreparation, other.dateDebutPreparation)
				&& Objects.equals(dateFinPreparation, other.dateFinPreparation) && etat == other.etat
				&& Objects.equals(idPanier, other.idPanier);
	}

	enum Etat {
		ATTENTE, VALIDEE, LIVRE
	}

}
