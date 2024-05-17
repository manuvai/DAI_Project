package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.keys.ApprovisionnerKey;

/**
 * Classe représentant la table d'association entre commande et article
 */
@Entity
@Table(name = "Approvisionner")
public class Approvisionner {

	/**
	 * Identifiant de la table
	 */
	@EmbeddedId
	private ApprovisionnerKey key;

	/**
	 * La quantité de la commande d'approvisionnement
	 */
	@Column(name = "QteApprovisionnement")
	private int qte;

	/**
	 * L'article à approvisionner
	 */
	@ManyToOne
	@JoinColumn(name = "IdArticle", insertable = false, updatable = false)
	private Article articleApprovisionner;

	/**
	 * La commande concernée
	 */
	@ManyToOne
	@JoinColumn(name = "IdCommande", insertable = false, updatable = false)
	private Commande commande;

	/**
	 * Constructeur vide.
	 */
	public Approvisionner() {

	}

	/**
	 * Constructeur complet.
	 *
	 * @param key
	 * @param qte
	 * @param articleApprovisionner
	 * @param commande
	 */
	public Approvisionner(final ApprovisionnerKey key, final int qte, final Article articleApprovisionner,
			final Commande commande) {
		this.key = key;
		this.qte = qte;
		this.articleApprovisionner = articleApprovisionner;
		this.commande = commande;
	}

	/**
	 * Récupération de la clé de l'association.
	 *
	 * @return
	 */
	public ApprovisionnerKey getKey() {
		return key;
	}

	/**
	 * MAJ de la clé de l'association.
	 *
	 * @param key
	 */
	public void setKey(final ApprovisionnerKey key) {
		this.key = key;
	}

	/**
	 * Récupération de la quantité de l'association.
	 *
	 * @return
	 */
	public int getQte() {
		return qte;
	}

	/**
	 * MAJ de la quantité de l'association.
	 *
	 * @param qte
	 */
	public void setQte(final int qte) {
		this.qte = qte;
	}

	/**
	 * Récupération de l'article de l'association.
	 *
	 * @return
	 */
	public Article getArticleApprovisionner() {
		return articleApprovisionner;
	}

	/**
	 * MAJ de l'article de l'association.
	 *
	 * @param articleApprovisionner
	 */
	public void setArticleApprovisionner(final Article articleApprovisionner) {
		this.articleApprovisionner = articleApprovisionner;
	}

	/**
	 * Récupération de la commande de l'association.
	 *
	 * @return
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * MAJ de la commande de l'association.
	 *
	 * @param commande
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
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Approvisionner other = (Approvisionner) obj;
		return Objects.equals(articleApprovisionner, other.articleApprovisionner)
				&& Objects.equals(commande, other.commande) && Objects.equals(key, other.key) && qte == other.qte;
	}

}
