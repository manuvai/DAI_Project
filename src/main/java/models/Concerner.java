package models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	/**
	 * Constructeur sans attribut
	 */
    public Concerner() {}
}
