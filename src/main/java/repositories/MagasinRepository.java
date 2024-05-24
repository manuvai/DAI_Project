package repositories;

import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;

import models.Article;
import models.Composer;
import models.Magasin;
import models.Panier;
import models.Stocker;

public class MagasinRepository extends AbstractRepository<Magasin, Integer> {

	public MagasinRepository() {
		super(Magasin.class);
	}

	/**
	 * MAJ du stock d'un magasin selon un panier donné.
	 *
	 * @param panier
	 * @param session
	 */
	public void updateStock(final Panier panier, final Session session) {

		final Magasin magasin = panier.getCreneau().getMagasin();

		for (final Entry<Article, Composer> entry : panier.getComposers().entrySet()) {
			final Article article = entry.getKey();
			final Composer composer = entry.getValue();

			final Map<Article, Stocker> stockers = magasin.getStockers();
			final Stocker stocker = stockers.get(article);

			final int nouvelleQte = Math.max(0, stocker.getQuantite() - composer.getQte());

			stocker.setQuantite(nouvelleQte);

			magasin.getStockers().put(article, stocker);

		}

		session.update(magasin);

	}

}
