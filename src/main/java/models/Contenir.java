package models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.keys.ContenirKey;

@Entity
@Table(name = "Contenir")
public class Contenir {

	@EmbeddedId
	private ContenirKey key;

	@Column(name = "Qte")
	private Integer qte;

	@ManyToOne
	@JoinColumn(name = "IdArticle", insertable = false, updatable = false)
	private Article article;

	/*
	 * @ManyToOne
	 *
	 * @JoinColumn(name = "IdListeCourse", insertable = false, updatable = false)
	 * private ListeDeCourse listeCourse;
	 *
	 */

}
