package models;

import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Article")
public class Article {

	/**
	 * Identifiant de l'article
	 */
	@Id
	@Column(name = "IdArticle")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idArticle;

	/**
	 * Libellé de l'article
	 */
	@Column(name = "LibArticle")
	private String lib;

	/**
	 * Description de l'article
	 */
	@Column(name = "DescArticle")
	private String desc;

	/**
	 * Prix unitaire de l'article
	 */
	@Column(name = "PrixUnitaire")
	private float prixUnitaire;

	/**
	 * Poids de l'article en grammes
	 */
	@Column(name = "Poids")
	private int poids;

	/**
	 * Nutriscore de l'article
	 */
	@Enumerated(EnumType.STRING)
	private Nutriscore nutriscore;

	/**
	 * sous categorie de l'article
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IdSousCat")
	private SousCategorie sousCategorie;

	@Transient
	private float prixKilo;

	//Mapping
	@MapKeyJoinColumn(name = "IdMagasin")
	@OneToMany(mappedBy = "articleStock", cascade = CascadeType.ALL)
	private Map<Magasin,Stocker> stockers;

	@MapKeyJoinColumn(name = "IdPanier")
	@OneToMany(mappedBy = "articleComposer", cascade = CascadeType.ALL)
	private Map<Panier, Composer> composers;

	/**
	 * Constructeur vide de l'article.
	 */
	public Article() {

	}

	/**
	 * Constructeur de l'article
	 * @param id identifiant de l'article
	 * @param lib libellé de l'article
	 * @param desc description de l'article
	 * @param prixUnitaire prix unitaire de l'article
	 * @param poids poids de l'article
	 * @param nutriscore nutriscore de l'article
	 */
	public Article(final Integer id, final String lib, final String desc, final float prixUnitaire, final int poids, final Nutriscore nutriscore) {
		idArticle = id;
		this.lib = lib;
		this.desc = desc;
		this.prixUnitaire = prixUnitaire;
		this.poids = poids;
		this.nutriscore = nutriscore;
		prixKilo = prixUnitaire*1000/poids;
	}

	/**
	 * Récupération de l'identifiant de l'article.
	 *
	 * @return id
	 */
	public Integer getId() {
		return idArticle;
	}

	/**
	 * Récupération de la description de l'article.
	 *
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}

	public void setDesc(final String inDesc) {
		desc = inDesc;
	}

	/**
	 * MAJ du lib de l'article.
	 *
	 * @param lib
	 */
	public void setNom(final String lib) {
		this.lib = lib;
	}

	/**
	 * @return the poids
	 */
	public int getPoids() {
		return poids;
	}

	/**
	 * @param poids the poids to set
	 */
	public void setPoids(final int poids) {
		this.poids = poids;
	}

	/**
	 * @return the prixUnitaire
	 */
	public float getPrixUnitaire() {
		return prixUnitaire;
	}

	/**
	 * @param prixUnitaire the prixUnitaire to set
	 */
	public void setPrixUnitaire(final float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	/**
	 * @return the sousCategorie
	 */
	public SousCategorie getSousCategorie() {
		return sousCategorie;
	}

	/**
	 * @param sousCategorie the sousCategorie to set
	 */
	public void setSousCategorie(final SousCategorie sousCategorie) {
		this.sousCategorie = sousCategorie;
	}

	/**
	 * Récupération du nutriscore de l'article.
	 *
	 * @return nutriscore
	 */
	public Nutriscore getNutriscore() {
		return nutriscore;
	}

	public void setNutriscore(final String nutriscoreLabel) {
		final Nutriscore nutriscore = switch (nutriscoreLabel) {
		case "A" -> Nutriscore.A;
		case "B" -> Nutriscore.B;
		case "C" -> Nutriscore.C;
		case "D" -> Nutriscore.D;
		case "E" -> Nutriscore.E;
		default -> null;
		};

		setNutriscore(nutriscore);
	}

	/**
	 * MAJ du nutriscore du produit.
	 *
	 * @param nutriscore
	 */
	public void setNutriscore(final Nutriscore nutriscore) {
		this.nutriscore = nutriscore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(desc, idArticle, lib, nutriscore, poids, prixKilo, prixUnitaire, sousCategorie);
	}


	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Article other = (Article) obj;
		return Objects.equals(desc, other.desc) && Objects.equals(idArticle, other.idArticle)
				&& Objects.equals(lib, other.lib) && nutriscore == other.nutriscore && poids == other.poids
				&& Float.floatToIntBits(prixKilo) == Float.floatToIntBits(other.prixKilo)
				&& Float.floatToIntBits(prixUnitaire) == Float.floatToIntBits(other.prixUnitaire)
				&& Objects.equals(sousCategorie, other.sousCategorie);
	}


	/**
	 * Nutriscore de A à E
	 */
	enum Nutriscore {
		A, B, C, D, E
	}
}
