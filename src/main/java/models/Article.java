package models;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
    @Transient
    private float prixKilo; 
    
    @MapKeyJoinColumn(name = "IdPanier")
    @OneToMany(mappedBy = "Panier", cascade = CascadeType.ALL)
    private Map<Article,Composer> validers;
    
    /**
     * Constructeur de l'article
     * @param id identifiant de l'article
     * @param lib libellé de l'article
     * @param desc description de l'article
     * @param prixUnitaire prix unitaire de l'article
     * @param poids poids de l'article
     * @param nutriscore nutriscore de l'article
     */
	public Article(Integer id, String lib, String desc, float prixUnitaire, int poids, Nutriscore nutriscore) {
		super();
		this.idArticle = id;
		this.lib = lib;
		this.desc = desc;
		this.prixUnitaire = prixUnitaire;
		this.poids = poids;
		this.nutriscore = nutriscore;
		this.prixKilo = prixUnitaire*1000/poids;
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

	/**
	 * MAJ du lib de l'article.
	 *
	 * @param lib
	 */
	public void setNom(final String lib) {
		this.lib = lib;
	}

	/**
	 * Récupération du nutriscore de l'article.
	 *
	 * @return nutriscore
	 */
	public Nutriscore getRole() {
		return nutriscore;
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((idArticle == null) ? 0 : idArticle.hashCode());
		result = prime * result + ((lib == null) ? 0 : lib.hashCode());
		result = prime * result + ((nutriscore == null) ? 0 : nutriscore.hashCode());
		result = prime * result + poids;
		result = prime * result + Float.floatToIntBits(prixKilo);
		result = prime * result + Float.floatToIntBits(prixUnitaire);
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
		Article other = (Article) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (idArticle == null) {
			if (other.idArticle != null)
				return false;
		} else if (!idArticle.equals(other.idArticle))
			return false;
		if (lib == null) {
			if (other.lib != null)
				return false;
		} else if (!lib.equals(other.lib))
			return false;
		if (nutriscore != other.nutriscore)
			return false;
		if (poids != other.poids)
			return false;
		if (Float.floatToIntBits(prixKilo) != Float.floatToIntBits(other.prixKilo))
			return false;
		if (Float.floatToIntBits(prixUnitaire) != Float.floatToIntBits(other.prixUnitaire))
			return false;
		return true;
	}

	/**
	 * Nutriscore de A à E
	 */
	enum Nutriscore {
		A, B, C, D, E
	}
}
