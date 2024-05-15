package repositories;

import models.Categorie;

public class CategorieRepository extends AbstractRepository<Categorie, Integer> {

	public CategorieRepository() {
		super(Categorie.class);
	}

}
