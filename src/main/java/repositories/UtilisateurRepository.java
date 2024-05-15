package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Utilisateur;

public class UtilisateurRepository extends AbstractRepository<Utilisateur, Integer> {

	public UtilisateurRepository() {
		super(Utilisateur.class);
	}
	
	public boolean findMail(final String mail) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		Utilisateur utilisateur =  (Utilisateur) session.createQuery("from Utilisateur where AdresseEmail = :mail").setParameter("mail", mail).uniqueResult();

		session.close();
		return utilisateur != null;
	}
	
	public Utilisateur findUserByMail(final String mail) {

		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		Utilisateur utilisateur =  (Utilisateur) session.createQuery("from Utilisateur where AdresseEmail = :mail").setParameter("mail", mail).uniqueResult();

		session.close();
		return utilisateur;
	}

}
