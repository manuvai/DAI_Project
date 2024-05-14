package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Composer")
public class Composer {
	@EmbeddedId
	private ComposerKey key;
	
	private int qte;
	
	@ManyToOne
	@JoinColumn(name = "IdArticle", insertable = false, updatable = false)
	private Article article;
	@ManyToOne
	@JoinColumn(name = "IdPanier", insertable = false, updatable = false)
	private Panier panier;

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
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((panier == null) ? 0 : panier.hashCode());
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
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (panier == null) {
			if (other.panier != null)
				return false;
		} else if (!panier.equals(other.panier))
			return false;
		if (qte != other.qte)
			return false;
		return true;
	}
}