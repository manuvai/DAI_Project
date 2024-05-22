package repositories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Session;

import dtos.ArticleStockDto;
import mappers.ArticleMapper;
import models.Article;
import models.Commande;
import models.Creneau;
import models.Magasin;
import models.Panier;
import models.Stocker;
import models.Utilisateur;

public class ArticleRepository extends AbstractRepository<Article, Integer> {

	public ArticleRepository() {
		super(Article.class);
	}

	/**
	 * Effectue une recherche d'article par leur nom.
	 *
	 * @param inName
	 * @return
	 */
	public Article findByName(final String inName) {

		Article article = null;

		if (inName != null) {
			final Session session = getSession();
			session.beginTransaction();

			article = findByName(inName, session);

			session.close();
		}

		return article;
	}

	/**
	 * Effectue une recherche d'article par leur nom à partir d'une session donnée.
	 *
	 * @param inName
	 * @param session
	 * @return
	 */
	public Article findByName(final String inName, final Session session) {
		Article article = null;

		if (inName != null && session != null) {
			final String query = "SELECT a "
					+ "FROM Article a "
					+ "WHERE a.lib = :lib";
			final Map<String, Object> mappedValues = Collections.singletonMap("lib", inName);
			final List<Article> results = getQueryResults(query, mappedValues, session);

			if (results != null && !results.isEmpty()) {
				article = results.get(0);

			}
		}

		return article;
	}

	/**
	 * Récupération des articles correspondant à la chaîne fournie.
	 *
	 * @param q
	 * @return
	 */
	public List<Article> search(final String q) {

		List<Article> articles = new ArrayList<>();

		if (q != null) {
			final String query = "SELECT a "
					+ "FROM Article a "
					+ "WHERE a.lib "
					+ "	LIKE :q ";
			final Map<String, Object> mappedValues = Collections.singletonMap("q", "%" + q + "%");

			articles = getQueryResults(query, mappedValues);

		}

		return articles;
	}

	/**
	 * Récupération des articles concernant un panier donné.
	 *
	 * @param panierId
	 * @return
	 */
	public List<Article> findArticlePanier(final Integer panierId) {
		final String query = "SELECT a "
				+ "FROM Article a, Composer c, Panier p "
				+ "WHERE a.idArticle = c.articleComposer "
				+ "AND c.panierComposer = p.idPanier "
				+ "AND p.idPanier = :panierId";
		final Map<String, Object> mappedValues = Collections.singletonMap("panierId", panierId.toString());

		return getQueryResults(query, mappedValues);
	}

	/**
	 * Récupération des articles concernant un rayon donné.
	 *
	 * @param nomRayon
	 * @return
	 */
	public List<Article> getArticlesByRayonName(final String nomRayon) {

		final String query = "SELECT a "
				+ "FROM Article a , SousCategorie sc, Categorie c, Rayon r "
				+ "WHERE a.sousCategorie = sc.idSousCat "
				+ "	AND sc.categorie = c.idCat "
				+ "	AND c.rayon = r.id "
				+ "	AND r.nomRayon = :nomRayon ";

		final Map<String, Object> mappedValues = Collections.singletonMap("nomRayon", nomRayon);

		return getQueryResults(query, mappedValues);

	}

	/**
	 * Récupération des articles les plus commandés.
	 *
	 * @param utilisateur
	 * @param limit
	 * @return
	 */
	public List<Article> findFrequentlyOrdered(final Utilisateur utilisateur, final int limit) {
		List<Article> articles = new ArrayList<>();

		if (utilisateur != null && limit > 0) {
			final String query = "SELECT a "
					+ "FROM Article a, Composer c, Panier p, Utilisateur u "
					+ "WHERE a = c.articleComposer "
					+ " AND c.panierComposer = p "
					+ " AND p.utilisateur = :userId "
					+ "GROUP BY a.idArticle "
					+ "ORDER BY SUM(c.qte) DESC "
					+ "LIMIT :limit";

			final Map<String, Object> mappedValues = new HashMap<>();
			mappedValues.put("userId", utilisateur.getId());
			mappedValues.put("limit", limit);

			articles = getQueryResults(query, mappedValues);
		}

		return articles;
	}

	/**
	 * Extraction de la liste des articles et leur stocks en fonction d'un magasin
	 * donné.
	 *
	 * @param magasinId
	 * @return
	 */
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
		final List<ArticleStockDto> articlesDtos = constructArticleDtos(paniersSurDeuxSemaines,
				commandesApprovisionnement, articles);

		session.close();
		return articlesDtos;
	}

	/**
	 * Construction de la liste d'articles avec le stock.
	 *
	 * @param paniersSurDeuxSemaines
	 * @param commandesApprovisionnement
	 * @param articles
	 * @return
	 */
	private List<ArticleStockDto> constructArticleDtos(final List<Panier> paniersSurDeuxSemaines,
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

					final Date nextDay = retrieveNextDay(date);
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

	/**
	 * Récupère la quantité commandée d'un article.
	 *
	 * @param date
	 * @param article
	 * @param commandesApprovisionnement
	 * @return
	 */
	private int retrieveQuantityOrdered(final Date date, final Article article,
			final List<Commande> commandesApprovisionnement) {
		int qte = 0;

		if (date != null && article != null && commandesApprovisionnement != null) {
			final Date nextDay = retrieveNextDay(date);
			final List<Commande> concernedCommandes = commandesApprovisionnement.stream()
					.filter(commande -> isBetween(commande.getDateArrivee(), date, nextDay))
					.filter(commande -> commande.getArticleApprovisionner().containsKey(article)).toList();

			for (final Commande commande : concernedCommandes) {
				qte += commande.getArticleApprovisionner().get(article).getQte();
			}
		}

		return qte;
	}

	/**
	 * Récupération de la quantité d'articles concernant des commandes client.
	 *
	 * @param date
	 * @param article
	 * @param paniers
	 * @return
	 */
	private int retrieveQuantityCut(final Date date, final Article article, final List<Panier> paniers) {
		int qte = 0;

		if (date != null && article != null && paniers != null) {
			final Date nextDay = retrieveNextDay(date);
			final List<Panier> concernedPaniers = paniers.stream()
					.filter(panier -> isBetween(panier.getCreneau().getDateCreneau(), date, nextDay))
					.filter(panier -> panier.getComposers().containsKey(article)).toList();

			for (final Panier panier : concernedPaniers) {
				qte += panier.getComposers().get(article).getQte();
			}
		}

		return qte;
	}

	/**
	 * Récupère la date sans heure définie
	 *
	 * @param inDate
	 * @return
	 */
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

	/**
	 * Détermine si une date fournie est comprise entre deux dates.
	 *
	 * @param givenDate
	 * @param date
	 * @param nextDay
	 * @return
	 */
	private boolean isBetween(final Date givenDate, final Date date, final Date nextDay) {
		return (date.before(givenDate) || date.equals(givenDate)) && nextDay.after(givenDate);
	}

	/**
	 * Récupération du jour suivant la date fournie
	 *
	 * @param date
	 * @return
	 */
	private Date retrieveNextDay(final Date date) {
		return date == null ? null : Date.from(date.toInstant().plus(Duration.ofDays(1)));
	}
}
