package repositories;

import models.Composer;
import models.keys.ComposerKey;

public class ComposerRepository extends AbstractRepository<Composer, ComposerKey> {

	public ComposerRepository() {
		super(Composer.class);
	}

}
