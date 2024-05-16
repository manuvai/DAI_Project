package models;

import java.util.Date;
import java.util.Map;

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

	// TODO Ajouter les constructeurs et compagnie

}
