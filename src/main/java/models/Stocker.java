package models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * La classe Stocker représente l'entité de stockage des articles dans les magasins.
 * Cette classe est annotée avec JPA pour permettre sa persistance dans une base de données.
 */
@Entity
@Table(name = "Stocker")
public class Stocker {

    @EmbeddedId
    private StockerKey key;

    @ManyToOne
    @JoinColumn(name = "IdMagasin", insertable = false, updatable = false)
    private Magasin magasinStock;

    @ManyToOne
    @JoinColumn(name = "IdArticle", insertable = false, updatable = false)
    private Article articleStock;

    @Column(name = "Quantite")
    private int quantite;

    /**
     * Constructeur par défaut de Stocker.
     * Initialise une nouvelle instance de la classe Stocker.
     */
    public Stocker() {}

    /**
     * Initialise une nouvelle instance de la classe Stocker avec les instances de Magasin et Article spécifiées.
     *
     * @param magasinStock l'instance de Magasin où l'article est stocké.
     * @param articleStock l'instance de Article stocké dans le magasin.
     */
    public Stocker(Magasin magasinStock, Article articleStock) {
        super();
        this.magasinStock = magasinStock;
        this.articleStock = articleStock;
    }

    /**
     * Obtient le magasin où l'article est stocké.
     *
     * @return l'instance de Magasin.
     */
    public Magasin getMagasinStock() {
        return magasinStock;
    }

    /**
     * Définit le magasin où l'article est stocké.
     *
     * @param magasinStock la nouvelle instance de Magasin.
     */
    public void setMagasinStock(Magasin magasinStock) {
        this.magasinStock = magasinStock;
    }

    /**
     * Obtient l'article stocké dans le magasin.
     *
     * @return l'instance de Article.
     */
    public Article getArticleStock() {
        return articleStock;
    }

    /**
     * Définit l'article stocké dans le magasin.
     *
     * @param articleStock la nouvelle instance de Article.
     */
    public void setArticleStock(Article articleStock) {
        this.articleStock = articleStock;
    }

    /**
     * Obtient la quantité de l'article stocké.
     *
     * @return la quantité d'articles.
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Définit la quantité de l'article stocké.
     *
     * @param quantite la nouvelle quantité d'articles.
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Calcule un code de hachage pour cet objet Stocker.
     *
     * @return une valeur de code de hachage pour cet objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(articleStock, magasinStock);
    }

    /**
     * Indique si un autre objet est "égal à" celui-ci.
     *
     * @param obj l'objet de référence avec lequel comparer.
     * @return true si cet objet est le même que l'argument obj ; false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Stocker other = (Stocker) obj;
        return Objects.equals(articleStock, other.articleStock) && Objects.equals(magasinStock, other.magasinStock);
    }
}
