package repositories;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Creneau;
import models.Panier;

public class PanierRepository extends AbstractRepository<Panier, Integer> {

	public PanierRepository() {
		super(Panier.class);
	}	 
	 
	@SuppressWarnings("unchecked")
	public List<Panier> findPanierByStatut(Enum<?> etat) {

			final Session session = getSession();
			final Transaction transaction = session.beginTransaction();

			final List<Panier> paniers = (List<Panier>) session.createQuery("from Panier where Etat = :etat and IdCreneau is not null").setParameter("etat", etat.toString()).list();
		
			transaction.commit();

			return paniers;
		}
	
	public List<Panier> findPanierByUserId(final int userid) {
	    final Session session = getSession();
	    final Transaction transaction = session.beginTransaction();
	    
	    List<Panier> paniers = session.createQuery("from Panier where UtilisateurId= :id", Panier.class)
	                                  .setParameter("id", userid)
	                                  .list();
	    
	    for (Panier panier : paniers) {
	        Hibernate.initialize(panier.getComposers());
	       
	    }
	    
	    transaction.commit();
	    session.close();
	    
	    return paniers;
	}

}
