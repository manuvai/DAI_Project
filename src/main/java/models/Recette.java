package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "Recette")
	public class Recette {
	public Recette() {}
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		

}
