package repositories;

import models.Concerner;
import models.keys.ConcernerKey;

public class ApprivisionnerRepository extends AbstractRepository<Concerner, ConcernerKey> {

	public ApprivisionnerRepository() {
		super(Concerner.class);
	}

}
