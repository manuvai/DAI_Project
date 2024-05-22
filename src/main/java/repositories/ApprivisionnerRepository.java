package repositories;

import models.Approvisionner;
import models.keys.ApprovisionnerKey;

public class ApprivisionnerRepository extends AbstractRepository<Approvisionner, ApprovisionnerKey > {

	public ApprivisionnerRepository() {
		super(Approvisionner.class);
	}

}
