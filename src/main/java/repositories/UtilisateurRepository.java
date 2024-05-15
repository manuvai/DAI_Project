package repositories;

import models.Utilisateur;

public class UtilisateurRepository extends AbstractRepository<Utilisateur, Integer> {

	public UtilisateurRepository() {
		super(Utilisateur.class);
	}

}
