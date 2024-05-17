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
	 * Chemin vers l'image du produit
	 */
	@Column(name = "CheminImage")
	private String cheminImage;

	/**
	 * Bio
	 */
	@Column(name = "Bio")
	private Boolean bio;



	/**
	 * Nutriscore de l'article
	 */
	@Enumerated(EnumType.STRING)
	private Nutriscore nutriscore;


	/**
	 * Promotion de l'article
	 */
	@Column(name = "Promotion", columnDefinition="Decimal(10,2) default '10'")

	private Float promotion;

	/**
	 * EAN d'un article
	 */
	@Column(name = "EAN")
	private String EAN;



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
	 * Association vers l'approvisionnement d'articles
	 */
	@MapKeyJoinColumn(name = "IdCommande")
	@OneToMany(mappedBy = "articleApprovisionner", cascade = CascadeType.ALL)
	private Map<Commande, Approvisionner> approvisionnements;

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
	public Article(final Integer id, final String lib, final String desc, final float prixUnitaire, final int poids, final Nutriscore nutriscore, final float promotion, final String EAN,final Boolean bio) {
		idArticle = id;
		this.lib = lib;
		this.desc = desc;
		this.prixUnitaire = prixUnitaire;
		this.poids = poids;
		this.nutriscore = nutriscore;
		prixKilo = prixUnitaire*1000/poids;
		this.promotion = promotion;
		this.EAN = EAN;
		this.bio = bio;

	}

	/**
	 * Récupération de l' EAN (European Article Number)
	 *
	 * @return EAN
	 */
	public String getEAN() {
		return EAN;
	}

	/**
	 * MAJ d'EAN.
	 *
	 * @param EAN
	 */
	public void setEAN(final String eAN) {
		EAN = eAN;
	}


	/**
	 * Récupération du boolean
	 *
	 * @return bio
	 */
	public Boolean getBio() {
		return bio;
	}

	/*
	 * MAJ du boolean bio
	 * @param bio
	 */
	public void setBio(final Boolean bio) {
		this.bio = bio;
	}


	/**
	 * Récupération de la promotion
	 *
	 * @return promoition
	 */
	public float getPromotion() {
		return promotion;
	}


	/**
	 * MAJ de la promotion.
	 *
	 * @param promotion
	 */
	public void setPromotion(final float promotion) {
		this.promotion = promotion;
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
	 * Récupération du libellé de l'article.
	 *
	 * @return lib
	 */
	public String getLib() {
		return lib;
	}

	/**
	 * Récupération de la description de l'article.
	 *
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * MAJ de la description de l'article.
	 *
	 * @param inDesc
	 */
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
	 * Récupération du poids de l'article.
	 *
	 * @return
	 */
	public int getPoids() {
		return poids;
	}

	/**
	 * MAJ du poids de l'article.
	 *
	 * @param poids
	 */
	public void setPoids(final int poids) {
		this.poids = poids;
	}

	/**
	 * Récupération du prix unitaire de l'article.
	 *
	 * @return
	 */
	public float getPrixUnitaire() {
		return prixUnitaire;
	}

	/**
	 * MAJ du prix unitaire de l'article.
	 *
	 * @param prixUnitaire
	 */
	public void setPrixUnitaire(final float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	/**
	 * Récupération du chemin vers l'image du produit.
	 *
	 * @return
	 */
	public String getCheminImage() {
		return cheminImage;
	}

	/**
	 * MAJ du chemin vers l'image du produit.
	 *
	 * @param cheminImage
	 */
	public void setCheminImage(final String cheminImage) {
		this.cheminImage = cheminImage;
	}

	/**
	 * Récupération de la sous catégorie de l'article.
	 *
	 * @return the sousCategorie
	 */
	public SousCategorie getSousCategorie() {
		return sousCategorie;
	}

	/**
	 * MAJ de la sous catégorie de l'article.
	 *
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

	/**
	 * MAJ du nutriscore à partir d'une string.
	 *
	 * @param nutriscoreLabel
	 */
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
