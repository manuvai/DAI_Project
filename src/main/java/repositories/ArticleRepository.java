package repositories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dtos.ArticleStockDto;
import mappers.ArticleMapper;
import models.Article;
import models.Commande;
import models.Creneau;
import models.Magasin;
import models.Panier;
import models.Stocker;

public class ArticleRepository extends AbstractRepository<Article, Integer> {

	public ArticleRepository() {
		super(Article.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findArticlePanier(Integer panierId) {

			final Session session = getSession();
			final Transaction transaction = session.beginTransaction();

			final List<Article> articles = (List<Article>) session.createQuery("SELECT a "
																			 + "FROM Article a, Composer c, Panier p "
																			 + "WHERE a.idArticle = c.articleComposer "
																			 + "AND c.panierComposer = p.idPanier "
																			 + "AND p.idPanier = :panierId").setParameter("panierId", panierId.toString()).list();
		
			transaction.commit();

			return articles;
		}
	
	public List<Article> getArticlesByRayonName(String nomRayon) {
		
		final Session session = getSession();

		final Transaction transaction = session.beginTransaction();

		final List<Article> result = session.createQuery("SELECT a FROM Article a , SousCategorie sc, Categorie c, Rayon r WHERE 		a.sousCategorie = sc.idSousCat AND sc.categorie = c.idCat AND c.rayon = r.id AND r.nomRayon = :nomRayon ").setParameter("nomRayon", 		nomRayon).list();

		session.close();
		return result;
	}

	public List<ArticleStockDto> extractArticleStock(final String magasinId) {
		Map<Article, Stocker> articles = new HashMap<>();
		final Session session = getSession();
		session.beginTransaction();

		Magasin magasin = null;
		final MagasinRepository magasinRepository = new MagasinRepository();

		// Récupération du magasin
		if (magasinId != null && !"".equals(magasinId)) {
			magasin = magasinRepository.findById(Integer.parseInt(magasinId), session);
		}

		List<Panier> paniersSurDeuxSemaines = new ArrayList<>();
		List<Commande> commandesApprovisionnement = new ArrayList<>();

		// Initialisation des données nécessaires
		if (magasin != null) {
			articles = magasin.getStockers();
			commandesApprovisionnement = extractCommandes(magasin.getCommandeApprovisionnement());
			paniersSurDeuxSemaines = extractPaniers(magasin.getCreneaux());
		}

		// Mapping des données
		final List<ArticleStockDto> articlesDtos = construireArticleDtos(paniersSurDeuxSemaines,
				commandesApprovisionnement, articles);

		session.close();
		return articlesDtos;
	}

	private List<ArticleStockDto> construireArticleDtos(final List<Panier> paniersSurDeuxSemaines,
			final List<Commande> commandesApprovisionnement, final Map<Article, Stocker> articles) {
		final List<ArticleStockDto> resultList = new ArrayList<>();

		if (articles != null && !articles.isEmpty()) {
			for (final Entry<Article, Stocker> entry : articles.entrySet()) {
				final Article article = entry.getKey();
				final Integer qteEnStock = entry.getValue().getQuantite();

				final ArticleStockDto dto = ArticleMapper.INSTANCE.articleToStockDto(article);

				Date date = transformOnlyDate(new Date());

				dto.getStocks().put(date, qteEnStock);

				for (int i = 1; i < 15; i++) {

					final Date nextDay = Date.from(date.toInstant().plus(Duration.ofDays(1)));
					final int qteCommandee = retrieveQuantityOrdered(date, article, commandesApprovisionnement);
					final int qteRetiree = retrieveQuantityCut(date, article, paniersSurDeuxSemaines);

					final int qtePrevue = dto.getStocks().get(date) + qteCommandee - qteRetiree;

					dto.getStocks().put(nextDay, qtePrevue);
					date = nextDay;
				}

				resultList.add(dto);

			}
		}

		return resultList;
	}

	private int retrieveQuantityCut(final Date date, final Article article, final List<Panier> paniersSurDeuxSemaines) {
		int qte = 0;

		if (date != null && article != null && paniersSurDeuxSemaines != null) {
			final Date nextDay = Date.from(date.toInstant().plus(Duration.ofDays(1)));
			final List<Panier> concernedPaniers = paniersSurDeuxSemaines.stream()
					.filter(panier -> (date.before(panier.getCreneau().getDateCreneau())
							|| date.equals(panier.getCreneau().getDateCreneau()))
							&& nextDay.after(panier.getCreneau().getDateCreneau()))
					.filter(panier -> panier.getComposers().containsKey(article))
					.toList();

			for (final Panier panier : concernedPaniers) {
				qte += panier.getComposers().get(article).getQte();
			}
		}

		return qte;
	}

	/**
	 * Extraction des commandes ayant eu lieu il y a au plus 15 jours.
	 *
	 * @param commandeApprovisionnement
	 * @return
	 */
	private List<Commande> extractCommandes(final Set<Commande> commandeApprovisionnement) {
		final Date now = new Date();
		final Date limit = Date.from(now.toInstant().plus(Duration.ofDays(15)));

		return commandeApprovisionnement == null
				? new ArrayList<>()
				: commandeApprovisionnement.stream()
						.filter(commande -> commande.getDateArrivee() != null)
						.filter(commande -> now.before(commande.getDateArrivee())
								&& limit.after(commande.getDateArrivee()))
						.toList();
	}

	/**
	 * Extraction des paniers ayant une date de créneau au plus 15 jours.
	 *
	 * @param creneaux
	 * @return
	 */
	private List<Panier> extractPaniers(final Set<Creneau> creneaux) {
		final Date now = new Date();
		final Date limit = Date.from(now.toInstant().plus(Duration.ofDays(15)));

		return extractPaniersByPeriod(creneaux, now, limit);
	}

	/**
	 * Extraction des paniers selon une période donnée.
	 *
	 * @param creneaux
	 * @param now
	 * @param limit
	 * @return
	 */
	private List<Panier> extractPaniersByPeriod(final Set<Creneau> creneaux, final Date now, final Date limit) {
		List<Creneau> creneauxSurDeuxSemaines = new ArrayList<>();

		if (creneaux != null) {
			creneauxSurDeuxSemaines = creneaux.stream()
					.filter(creneau -> creneau.getDateCreneau() != null)
					.filter(creneau -> now.before(creneau.getDateCreneau())
							&& limit.after(creneau.getDateCreneau()))
					.toList();
		}

		return creneauxSurDeuxSemaines.stream().map(Creneau::getPaniers).flatMap(Set::stream).toList();
	}

	private int retrieveQuantityOrdered(final Date date, final Article article,
			final List<Commande> commandesApprovisionnement) {
		int qte = 0;

		if (date != null && article != null && commandesApprovisionnement != null) {
			final Date nextDay = Date.from(date.toInstant().plus(Duration.ofDays(1)));
			final List<Commande> concernedCommandes = commandesApprovisionnement.stream()
					.filter(commande -> (date.before(commande.getDateArrivee())
							|| date.equals(commande.getDateArrivee())) && nextDay.after(commande.getDateArrivee()))
					.filter(commande -> commande.getArticleApprovisionner().containsKey(article)).toList();

			for (final Commande commande : concernedCommandes) {
				qte += commande.getArticleApprovisionner().get(article).getQte();
			}
		}

		return qte;
	}

	private Date transformOnlyDate(final Date inDate) {
		final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = formatter.parse(formatter.format(Date.from(inDate.toInstant())));
		} catch (final ParseException e) {
			date = null;
		}
		return date;
	}
}
