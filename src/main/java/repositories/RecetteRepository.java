package repositories;

import models.Recette;

public class RecetteRepository extends AbstractRepository<Recette, Integer> {

	public RecetteRepository() {
		super(Recette.class);
	}

}
