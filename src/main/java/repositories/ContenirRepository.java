package repositories;

import models.Contenir;
import models.keys.ContenirKey;

public class ContenirRepository extends AbstractRepository<Contenir, ContenirKey> {

	public ContenirRepository() {
		super(Contenir.class);
	}

}
