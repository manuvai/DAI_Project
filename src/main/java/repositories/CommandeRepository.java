package repositories;

import models.Concerner;
import models.keys.ConcernerKey;

public class CommandeRepository extends AbstractRepository<Concerner, ConcernerKey> {

	public CommandeRepository() {
		super(Concerner.class);
	}

}
