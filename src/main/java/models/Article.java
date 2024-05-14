package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "LibArticle")
	private String lib;
	
	@Column(name = "DescArticle")
	private String desc;

	@Column(name = "PrixUnitaire")
	private float prixUnitaire;

	@Column(name = "Poids")
	private int poids;

	@Enumerated(EnumType.STRING)
	private Nutriscore nutriscore;
	
    @Transient
    private float prixKilo; 
    
	public Article(Integer id, String lib, String desc, float prixUnitaire, int poids, Nutriscore nutriscore) {
		super();
		this.id = id;
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
		return id;
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
	 * @return
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	enum Nutriscore {
		A, B, C, D, E
	}
}
