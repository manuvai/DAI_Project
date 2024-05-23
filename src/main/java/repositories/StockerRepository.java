package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import models.Stocker;
import models.keys.StockerKey;

public class StockerRepository extends AbstractRepository<Stocker, StockerKey> {

	public StockerRepository() {
		super(Stocker.class);
	}

	public int getQuantiteByArticleAndMagasin(int articleId, int magasinId) {
		final Session session = getSession();
		final Transaction transaction = session.beginTransaction();	
		
		String queryString  = "SELECT s.quantite "
		 		+ "FROM Stocker s "
		 		+ "WHERE s.articleStock = :idA "
		 		+ "AND s.magasinStock = :idM";
		 
		 Query query = session.createQuery(queryString);
		 query.setParameter("idA", articleId);
		 query.setParameter("idM", magasinId);
		 
		 if(query.uniqueResult()!=null) {
			 int qte =  (int) query.uniqueResult();
			 
				session.close();
				return qte;
		 }
		 
		 
		 
		session.close();
		return 0;
    }
}
