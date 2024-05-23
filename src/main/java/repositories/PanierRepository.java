package repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Panier;

public class PanierRepository extends AbstractRepository<Panier, Integer> {

	public PanierRepository() {
		super(Panier.class);
	}

	@SuppressWarnings("unchecked")
	public List<Panier> findPanierByStatut(final Enum<?> etat) {

			final Session session = getSession();
			final Transaction transaction = session.beginTransaction();

			final List<Panier> paniers = session.createQuery("from Panier where Etat = :etat and IdCreneau is not null").setParameter("etat", etat.toString()).list();

			transaction.commit();

			return paniers;
		}

	public List<Panier> findPanierByUserId(final int userid) {
	    final Session session = getSession();
	    final Transaction transaction = session.beginTransaction();

	    final List<Panier> paniers = session.createQuery("from Panier where UtilisateurId= :id", Panier.class)
	                                  .setParameter("id", userid)
	                                  .list();

	    for (final Panier panier : paniers) {
	        Hibernate.initialize(panier.getComposers());

	    }

	    transaction.commit();
	    session.close();

	    return paniers;
	}

	/**
	 * Récupération du temps moyen de préparation de paniers
	 *
	 * @return
	 */
	public long getAverageTempsPreparation() {

		final String query = "SELECT p "
				+ "FROM Panier p "
				+ "WHERE dateDebutPreparation IS NOT NULL "
				+ "	AND dateFinPreparation IS NOT NULL";
		final List<Panier> paniers = getQueryResults(query);

		final List<Long> periodPaniers = paniers == null
				? new ArrayList<>()
				: paniers.stream()
						.map(this::getDateDiff)
						.toList();
		final Long periodSum = periodPaniers.stream()
				.mapToLong(Long::longValue)
				.sum();

		return periodSum / periodPaniers.size();
	}

	/**
	 * Récupération de la différence de temps de préparation d'un panier.
	 *
	 * @param panier
	 * @return
	 */
	private long getDateDiff(final Panier panier) {
		return panier == null
				? 0
				: getDateDiff(panier.getDateDebutPreparation(), panier.getDateFinPreparation());
	}

	/**
	 * Récupération de la différence entre deux dates.
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	private long getDateDiff(final Date start, final Date end) {
		long dateDiff = 0;

		if (start != null && end != null) {
			dateDiff = end.getTime() - start.getTime();
		}

		return dateDiff;
	}
	


}
