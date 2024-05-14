package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PostIt")
public class PostIt {

	public PostIt() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPostIt;

	/**
	 * getter for postit id
	 * @return id liste
	 */
	public Integer getIdPostIt() {
		return idPostIt;
	}
	
	
}