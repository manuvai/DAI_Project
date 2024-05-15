package repositories;

import models.PostIt;

public class PostItRepository extends AbstractRepository<PostIt, Integer> {

	public PostItRepository() {
		super(PostIt.class);
	}

}
