package repositories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import models.SousCategorie;

public class SousCategorieRepository extends AbstractRepository<SousCategorie, Integer> {

	public SousCategorieRepository() {
		super(SousCategorie.class);
	}
	
	
	/**
	 * Récupération des sous-catégories concernant une catégorie donnée.
	 *
	 * @param nomRayon
	 * @return
	 */
	public List<SousCategorie>getSousCategoriesByCategorieName(final String nomCategorie) {

		final String query = "SELECT sc "
				+ "FROM SousCategorie sc, Categorie c "
				+ "WHERE sc.categorie = c.idCat "
				+ "AND c.nomCategorie = :nomCategorie ";

		final Map<String, Object> mappedValues = Collections.singletonMap("nomCategorie", nomCategorie);

		return getQueryResults(query, mappedValues);
	}
}
