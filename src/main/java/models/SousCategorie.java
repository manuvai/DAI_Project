package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Classe représentant une categorie
 */
@Entity(name = "SousCategorie")
public class SousCategorie {
	/**
	 * id d'une sous-categorie
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "IdSousCat")
	private Integer idSousCat;
	/**
	 * nom d'une sous-categorie
	 */
	@Column(name = "NomSousCategorie")
	private String nomSousCategorie;
	
	/**
	 * categorie de la sous-categorie
	 */
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "IdCat")  
	private Categorie categorie;

	/**
	 * articles
	 */
	@OneToMany(mappedBy = "sousCategorie", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<Article> articles = new HashSet(0);
	
	/**
	 * retourne l id de la sous categorie
	 * @return id de la sous categorie
	 */
	public Integer getIdSousCat() {
		return idSousCat;
	}
	/**
	 * retourne le nom de la sous categorie
	 * @return nom de la sous categorie
	 */

	public String getNomSousCategorie() {
		return nomSousCategorie;
	}
	/**
	 * retourne la categorie de la sous categorie
	 * @return categorie de la sous categorie
	 */

	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * retourne les articles
	 * @return article
	 */
	public Set<Article> getArticles() {
		return articles;
	}
	
	
	/**
	 * ajoute un article
	 * @param article à ajouter
	 */
	public void addArticles(Article article) {
		this.articles.add(article);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categorie, idSousCat, nomSousCategorie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SousCategorie other = (SousCategorie) obj;
		return Objects.equals(categorie, other.categorie) && Objects.equals(idSousCat, other.idSousCat)
				&& Objects.equals(nomSousCategorie, other.nomSousCategorie);
	}
	
	

}
