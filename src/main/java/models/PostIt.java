package models;

import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PostIt")
public class PostIt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPostIt;

	@Column(name = "Label")
	private String label;

	@MapKeyJoinColumn(name = "IdListeDeCourse")
	@OneToMany(mappedBy = "postit", cascade = CascadeType.ALL)
	private Map<ListeDeCourse, Concerner> stockers;

	public PostIt() {
	}


	/**
	 * getter for postit id
	 * @return id liste
	 */
	public Integer getIdPostIt() {
		return idPostIt;
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
	 * @param idPostIt the idPostIt to set
	 */
	public void setIdPostIt(final Integer idPostIt) {
		this.idPostIt = idPostIt;
	}

	/**
	 * @return the stockers
	 */
	public Map<ListeDeCourse, Concerner> getStockers() {
		return stockers;
	}

	/**
	 * @param stockers the stockers to set
	 */
	public void setStockers(final Map<ListeDeCourse, Concerner> stockers) {
		this.stockers = stockers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPostIt, label);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final PostIt other = (PostIt) obj;
		return Objects.equals(idPostIt, other.idPostIt) && Objects.equals(label, other.label);
	}

}