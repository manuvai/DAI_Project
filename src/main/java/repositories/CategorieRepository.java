package repositories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import models.Article;
import models.Categorie;

public class CategorieRepository extends AbstractRepository<Categorie, Integer> {

	public CategorieRepository() {
		super(Categorie.class);
	}
	
	/**
	 * Récupération des catégories concernant un rayon donné.
	 *
	 * @param nomRayon
	 * @return
	 */
	public List<Categorie>getCategoriesByRayonName(final String nomRayon) {

		final String query = "SELECT c "
				+ "FROM Categorie c, Rayon r "
				+ "WHERE c.rayon = r.id "
				+ "	AND r.nomRayon = :nomRayon ";

		final Map<String, Object> mappedValues = Collections.singletonMap("nomRayon", nomRayon);

		return getQueryResults(query, mappedValues);

	}
	
	/**
	 * Récupération des sous-catégories concernant une catégorie donnée.
	 *
	 * @param nomRayon
	 * @return
	 */
	public List<Categorie>getSousCategoriesByCategorieName(final String nomCategorie) {

		final String query = "SELECT sc "
				+ "FROM SousCategorie sc, Categorie c "
				+ "WHERE a.sousCategorie = sc.idSousCat "
				+ "AND sc.categorie = c.idCat";

		final Map<String, Object> mappedValues = Collections.singletonMap("nomCategorie", nomCategorie);

		return getQueryResults(query, mappedValues);

	}

}
