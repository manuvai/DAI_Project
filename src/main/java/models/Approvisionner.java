package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.keys.ApprovisionnerKey;

@Entity
@Table(name = "Approvisionner")
public class Approvisionner {

	@EmbeddedId
	private ApprovisionnerKey key;

	@Column(name = "QteApprovisionnement")
	private int qte;

	@ManyToOne
	@JoinColumn(name = "IdArticle", insertable = false, updatable = false)
	private Article articleApprovisionner;

	@ManyToOne
	@JoinColumn(name = "IdCommande", insertable = false, updatable = false)
	private Commande commande;

	public Approvisionner() {

	}

	public Approvisionner(final ApprovisionnerKey key, final int qte, final Article articleApprovisionner,
			final Commande commande) {
		this.key = key;
		this.qte = qte;
		this.articleApprovisionner = articleApprovisionner;
		this.commande = commande;
	}

	/**
	 * @return the key
	 */
	public ApprovisionnerKey getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(final ApprovisionnerKey key) {
		this.key = key;
	}

	/**
	 * @return the qte
	 */
	public int getQte() {
		return qte;
	}

	/**
	 * @param qte the qte to set
	 */
	public void setQte(final int qte) {
		this.qte = qte;
	}

	/**
	 * @return the articleApprovisionner
	 */
	public Article getArticleApprovisionner() {
		return articleApprovisionner;
	}

	/**
	 * @param articleApprovisionner the articleApprovisionner to set
	 */
	public void setArticleApprovisionner(final Article articleApprovisionner) {
		this.articleApprovisionner = articleApprovisionner;
	}

	/**
	 * @return the commande
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * @param commande the commande to set
	 */
	public void setCommande(final Commande commande) {
		this.commande = commande;
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleApprovisionner, commande, key, qte);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final Approvisionner other = (Approvisionner) obj;
		return Objects.equals(articleApprovisionner, other.articleApprovisionner)
				&& Objects.equals(commande, other.commande) && Objects.equals(key, other.key) && qte == other.qte;
	}

}
