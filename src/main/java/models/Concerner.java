package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.keys.ConcernerKey;

@Entity
@Table(name = "Concerner")
public class Concerner {

	/**
	 * Id d'une ligne de Concerner
	 */
    @EmbeddedId
    private ConcernerKey key;

    /**
     * Quantite désirée du produit en PostIt
     */
    @Column(name="Quantite")
    private int quantitePostIt;

	/**
	 * Id du post it
	 */
	@ManyToOne
	@JoinColumn(name = "idPostIt", insertable = false, updatable = false)
	private PostIt postit;

	/**
	 * Id de la liste de course
	 */
	@ManyToOne
	@JoinColumn(name = "idListeDeCourse", insertable = false, updatable = false)
	private ListeDeCourse liste;

	public Concerner(final ListeDeCourse liste, final PostIt postit, final int quantitePostIt) {
		this.liste = liste;
		this.postit = postit;
		this.quantitePostIt = quantitePostIt;
		key = new ConcernerKey(postit.getIdPostIt(), liste.getIdListDeCourse());
	}

	/**
	 * Constructeur sans attribut
	 */
	public Concerner() {
	}

    /**
     * getter de quantité
     * @return quantité
     */
    public int getQuantitePostIt() {
		return quantitePostIt;
	}

    /**
     * setter de quantite
     * @param quantitePostIt
     */
	public void setQuantitePostIt(final int quantitePostIt) {
		this.quantitePostIt = quantitePostIt;
	}

    /**
     * getter de postit
     * @return postit
     */
	public PostIt getPostit() {
		return postit;
	}

    /**
     * setter de postit
     * @param postit
     */
	public void setPostit(final PostIt postit) {
		this.postit = postit;
	}

    /**
     * getter de liste
     * @return liste
     */

	public ListeDeCourse getListe() {
		return liste;
	}

    /**
     * setter de liste
     * @param liste
     */
	public void setListe(final ListeDeCourse liste) {
		this.liste = liste;
	}

	/**
	 * @return the key
	 */
	public ConcernerKey getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(final ConcernerKey key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, liste, postit, quantitePostIt);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final Concerner other = (Concerner) obj;
		return Objects.equals(key, other.key) && Objects.equals(liste, other.liste)
				&& Objects.equals(postit, other.postit) && quantitePostIt == other.quantitePostIt;
	}

}
