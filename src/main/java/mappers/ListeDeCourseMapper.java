package mappers;

import java.util.Map.Entry;

import dtos.ListeCourseDto;
import models.Concerner;
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

		}

		return liste;

	}

}
