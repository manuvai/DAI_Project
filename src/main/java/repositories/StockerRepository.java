package repositories;

import models.Stocker;
import models.keys.StockerKey;

public class StockerRepository extends AbstractRepository<Stocker, StockerKey> {

	public StockerRepository() {
		super(Stocker.class);
	}

}
