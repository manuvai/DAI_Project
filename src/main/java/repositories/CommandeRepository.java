package repositories;

import models.Commande;
import models.Concerner;
import models.keys.ConcernerKey;

public class CommandeRepository extends AbstractRepository<Commande, Integer> {

	public CommandeRepository() {
		super(Commande.class);
	}

}
