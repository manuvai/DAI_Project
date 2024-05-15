package repositories;

import models.Concerner;
import models.keys.ConcernerKey;

public class ConcernerRepository extends AbstractRepository<Concerner, ConcernerKey> {

	public ConcernerRepository() {
		super(Concerner.class);
	}

}
