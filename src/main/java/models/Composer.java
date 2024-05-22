package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.keys.ComposerKey;

@Entity
@Table(name = "Composer")
public class Composer {
	@EmbeddedId
	private ComposerKey key;
	
	private int qte;
	
	@ManyToOne
	@JoinColumn(name = "IdArticle", insertable = false, updatable = false)
	private Article articleComposer;
	
	@ManyToOne
	@JoinColumn(name = "IdPanier", insertable = false, updatable = false)
	private Panier panierComposer;

	public Composer() {}

	public Composer(ComposerKey key, int qte) {
		setKey(key);
		setQte(qte);
	}

	public ComposerKey getKey() {
		return key;
	}

	public void setKey(ComposerKey key) {
		this.key = key;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleComposer == null) ? 0 : articleComposer.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((panierComposer == null) ? 0 : panierComposer.hashCode());
		result = prime * result + qte;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Composer other = (Composer) obj;
		if (articleComposer == null) {
			if (other.articleComposer != null)
				return false;
		} else if (!articleComposer.equals(other.articleComposer))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (panierComposer == null) {
			if (other.panierComposer != null)
				return false;
		} else if (!panierComposer.equals(other.panierComposer))
			return false;
		if (qte != other.qte)
			return false;
		return true;
	}
}