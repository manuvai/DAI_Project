package models;

import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe représentant la table pour approvisionner les produits.
 */
@Entity
@Table(name = "Commande")
public class Commande {
	/**
	 * Identifiant de la commande.
	 */
	@Id
	@Column(name = "IdCommande")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Date d'arrivée de la commande.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DateArriveeCommande")
	private Date dateArrivee;

	/**
	 * Date de création de la commande.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DateCreationCommande")
	private Date dateCreation;

	/**
	 * Liaison vers le magasin originaire de la commande.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IdMagasin")
	private Magasin magasin;

	/**
	 * Liaison vers l'association d'approvisionnements
	 */
	@MapKeyJoinColumn(name = "IdArticle")
	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
	private Map<Article, Approvisionner> articleApprovisionner;

	/**
	 * Constructeur vide.
	 */
	public Commande() {

	}

	/**
	 * Constructeur complet.
	 *
	 * @param id
	 * @param dateArrivee
	 * @param dateCreation
	 * @param magasin
	 * @param articleApprovisionner
	 */
	public Commande(final Date dateArrivee, final Magasin magasin) {
		this.dateArrivee = dateArrivee;
		this.dateCreation = new Date();
		this.magasin = magasin;
		this.articleApprovisionner = new HashMap<Article, Approvisionner>();
	}

	/**
	 * Récupération de l'identifiant de la commande.
	 *
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * MAJ de l'identifiant de la commande.
	 *
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * Récupération de la date d'arrivée de la commande.
	 *
	 * @return
	 */
	public Date getDateArrivee() {
		return dateArrivee;
	}

	/**
	 * MAJ de la date d'arrivée de la commande.
	 *
	 * @param dateArrivee
	 */
	public void setDateArrivee(final Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	/**
	 * Récupération de la date de création de la commande.
	 *
	 * @return
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * MAJ de la date de création de la commande.
	 *
	 * @param dateCreation
	 */
	public void setDateCreation(final Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * Récupération du magasin de la commande.
	 *
	 * @return
	 */
	public Magasin getMagasin() {
		return magasin;
	}

	/**
	 * MAJ du magasin de la commande.
	 *
	 * @param magasin
	 */
	public void setMagasin(final Magasin magasin) {
		this.magasin = magasin;
	}

	/**
	 * Récupération des approvisionnements de la commande.
	 *
	 * @return
	 */
	public Map<Article, Approvisionner> getArticleApprovisionner() {
		return articleApprovisionner;
	}

	/**
	 * MAJ des approvisionnements de la commande.
	 *
	 * @param articleApprovisionner
	 */
	public void setArticleApprovisionner(final Map<Article, Approvisionner> articleApprovisionner) {
		this.articleApprovisionner = articleApprovisionner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateArrivee, dateCreation, id, magasin);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Commande other = (Commande) obj;
		return Objects.equals(dateArrivee, other.dateArrivee) && Objects.equals(dateCreation, other.dateCreation)
				&& Objects.equals(id, other.id) && Objects.equals(magasin, other.magasin);
	}



}
