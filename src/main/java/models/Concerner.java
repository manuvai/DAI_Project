package models;

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
	public void setQuantitePostIt(int quantitePostIt) {
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
	public void setPostit(PostIt postit) {
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
	public void setListe(ListeDeCourse liste) {
		this.liste = liste;
	}

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
    
	public Concerner(ListeDeCourse liste, PostIt postit, int quantitePostIt) {
		this.liste = liste;
		this.postit = postit;
		this.quantitePostIt = quantitePostIt;
		this.key = new ConcernerKey(postit.getIdPostIt(), liste.getIdListDeCourse());
	}

	/**
	 * Constructeur sans attribut
	 */
    public Concerner() {}
}
