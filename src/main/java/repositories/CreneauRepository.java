package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Creneau;
import models.Utilisateur;

public class CreneauRepository extends AbstractRepository<Creneau, Integer> {

	public CreneauRepository() {
		super(Creneau.class);
	}
	
	public List<Creneau> findCreneauByMagasin(final int magasin) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		List<Creneau> creneaux =  (List<Creneau>) session.createQuery("from Creneau where IdMagasin = :id").setParameter("id", magasin).list();

		session.close();
		return creneaux;
	}

}
