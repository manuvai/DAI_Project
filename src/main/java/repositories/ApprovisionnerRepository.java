package repositories;

import models.Approvisionner;
import models.keys.ApprovisionnerKey;

public class ApprovisionnerRepository extends AbstractRepository<Approvisionner, ApprovisionnerKey > {

	public ApprovisionnerRepository() {
		super(Approvisionner.class);
	}

}
