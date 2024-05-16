package repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Composer;
import models.keys.ComposerKey;

public class ComposerRepository extends AbstractRepository<Composer, ComposerKey> {

	public ComposerRepository() {
		super(Composer.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Composer> findArticlePanier(Integer panierId) {

			final Session session = getSession();
			final Transaction transaction = session.beginTransaction();

			final List<Composer> composers = (List<Composer>) session.createQuery("SELECT c "
																			 + "FROM Article a, Composer c, Panier p "
																			 + "WHERE a.idArticle = c.articleComposer "
																			 + "AND c.panierComposer = p.idPanier "
																			 + "AND p.idPanier = :panierId").setParameter("panierId", panierId.toString()).list();
		
			transaction.commit();

			return composers;
		}

}
