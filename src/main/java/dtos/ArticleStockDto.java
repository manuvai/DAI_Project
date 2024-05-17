package dtos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe contenant les informations à afficher pour le stock d'un article.
 */
public class ArticleStockDto {

	/**
	 * Identifiant de l'article.
	 */
	private String id;

	/**
	 * Nom de l'article.
	 */
	private String label;

	/**
	 * Chemin vers l'image de l'article.
	 */
	private String imagePath;

	/**
	 * Stocks de l'article en fonction de la date.
	 */
	private Map<Date, Integer> stocks = new HashMap<>();

	/**
	 * Constructeur sans stocks
	 *
	 * @param id
	 * @param label
	 * @param imagePath
	 */
	public ArticleStockDto(final Integer id, final String label, final String imagePath) {
		this(Integer.toString(id), label, imagePath, new HashMap<>());
	}

	/**
	 * Constructeur sans stocks
	 *
	 * @param id
	 * @param label
	 * @param imagePath
	 */
	public ArticleStockDto(final String id, final String label, final String imagePath) {
		this(id, label, imagePath, new HashMap<>());
	}

	/**
	 * Constructeur avec tous les champs
	 *
	 * @param id
	 * @param label
	 * @param imagePath
	 * @param stocks
	 */
	public ArticleStockDto(final String id, final String label, final String imagePath,
			final Map<Date, Integer> stocks) {
		this.id = id;
		this.label = label;
		this.imagePath = imagePath;
		this.stocks = stocks;
	}

	/**
	 * Récupération de l'identifiant de l'article.
	 *
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * MAJ de l'identifiant de l'article.
	 *
	 * @param id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Récupération du nom de l'article.
	 *
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * MAJ du nom de l'article.
	 *
	 * @param label
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * Récupération de l'image de l'article.
	 *
	 * @return
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * MAJ de l'image de l'article.
	 *
	 * @param imagePath
	 */
	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Récupération des stocks de l'article.
	 *
	 * @return
	 */
	public Map<Date, Integer> getStocks() {
		return stocks;
	}

	/**
	 * MAJ des stocks de l'article.
	 *
	 * @param stocks
	 */
	public void setStocks(final Map<Date, Integer> stocks) {
		this.stocks = stocks;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, imagePath, label, stocks);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ArticleStockDto other = (ArticleStockDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(imagePath, other.imagePath)
				&& Objects.equals(label, other.label) && Objects.equals(stocks, other.stocks);
	}

}
