package mappers;

import java.util.Map.Entry;

import dtos.ListeCourseDto;
import models.Article;
import models.Concerner;
import models.Contenir;
import models.ListeDeCourse;
import models.PostIt;

public class ListeDeCourseMapper {
	public static final ListeDeCourseMapper INSTANCE = new ListeDeCourseMapper();

	public ListeCourseDto listeToDto(final ListeDeCourse inListe) {
		ListeCourseDto liste = null;

		if (inListe != null) {
			liste = new ListeCourseDto();
			liste.setId(inListe.getIdListDeCourse());
			liste.setNom(inListe.getNom());

			if (inListe.getConcerners() != null) {
				for (final Entry<PostIt, Concerner> concerners : inListe.getConcerners().entrySet()) {

					final Concerner concerner = concerners.getValue();
					final PostIt postIt = concerners.getKey();

					final int value = concerner.getQuantitePostIt();

					liste.getPostsItsMap().put(postIt, value);
				}
			}

			if (inListe.getContenirs() != null) {
				for (final Entry<Article, Contenir> entry : inListe.getContenirs().entrySet()) {
					final Contenir contenir = entry.getValue();
					final Article article = entry.getKey();

					final int value = contenir.getQte();

					liste.getArticlesMap().put(article, value);
				}
			}

		}

		return liste;

	}

}
