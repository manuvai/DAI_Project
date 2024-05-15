package repositories;

import models.Panier;

public class PanierRepository extends AbstractRepository<Panier, Integer> {

	public PanierRepository() {
		super(Panier.class);
	}

}
