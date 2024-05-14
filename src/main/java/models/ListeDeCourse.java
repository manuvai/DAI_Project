package models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ListeDeCourse")
public class ListeDeCourse {
	public ListeDeCourse() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idListDeCourse;

	/**
	 * getter for liste de course id
	 * @return id liste
	 */
	public Integer getIdListDeCourse() {
		return idListDeCourse;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idListDeCourse);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final ListeDeCourse other = (ListeDeCourse) obj;
		return Objects.equals(idListDeCourse, other.idListDeCourse);
	}

}
