package models;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Commande")
public class Commande {
	@Id
	@Column(name = "IdCommande")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "DateArriveeCommande")
	private Date dateArrivee;

	@Temporal(TemporalType.DATE)
	@Column(name = "DateCreationCommande")
	private Date dateCreation;

	@ManyToOne
	@JoinTable(name = "IdMagasin")
	private Magasin magasin;

	@MapKeyJoinColumn(name = "IdArticle")
	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
	private Map<Article, Approvisionner> articleApprovisionner;

	public Commande() {

	}

	public Commande(final Integer id, final Date dateArrivee, final Date dateCreation, final Magasin magasin,
			final Map<Article, Approvisionner> articleApprovisionner) {
		this.id = id;
		this.dateArrivee = dateArrivee;
		this.dateCreation = dateCreation;
		this.magasin = magasin;
		this.articleApprovisionner = articleApprovisionner;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the dateArrivee
	 */
	public Date getDateArrivee() {
		return dateArrivee;
	}

	/**
	 * @param dateArrivee the dateArrivee to set
	 */
	public void setDateArrivee(final Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(final Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the magasin
	 */
	public Magasin getMagasin() {
		return magasin;
	}

	/**
	 * @param magasin the magasin to set
	 */
	public void setMagasin(final Magasin magasin) {
		this.magasin = magasin;
	}

	/**
	 * @return the articleApprovisionner
	 */
	public Map<Article, Approvisionner> getArticleApprovisionner() {
		return articleApprovisionner;
	}

	/**
	 * @param articleApprovisionner the articleApprovisionner to set
	 */
	public void setArticleApprovisionner(final Map<Article, Approvisionner> articleApprovisionner) {
		this.articleApprovisionner = articleApprovisionner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleApprovisionner, dateArrivee, dateCreation, id, magasin);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final Commande other = (Commande) obj;
		return Objects.equals(articleApprovisionner, other.articleApprovisionner)
				&& Objects.equals(dateArrivee, other.dateArrivee) && Objects.equals(dateCreation, other.dateCreation)
				&& Objects.equals(id, other.id) && Objects.equals(magasin, other.magasin);
	}



}
