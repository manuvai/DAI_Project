package dtos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArticleStockDto {

	private String id;
	private String label;
	private String imagePath;
	private Map<Date, Integer> stocks = new HashMap<>();

	public ArticleStockDto(final Integer id, final String label, final String imagePath) {
		this(Integer.toString(id), label, imagePath, new HashMap<>());
	}

	public ArticleStockDto(final String id, final String label, final String imagePath) {
		this(id, label, imagePath, new HashMap<>());
	}

	public ArticleStockDto(final String id, final String label, final String imagePath,
			final Map<Date, Integer> stocks) {
		this.id = id;
		this.label = label;
		this.imagePath = imagePath;
		this.stocks = stocks;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the stocks
	 */
	public Map<Date, Integer> getStocks() {
		return stocks;
	}

	/**
	 * @param stocks the stocks to set
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
