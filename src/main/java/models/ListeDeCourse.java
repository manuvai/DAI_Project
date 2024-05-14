package models;

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

}
