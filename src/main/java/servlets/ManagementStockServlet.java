package servlets;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dtos.ArticleStockDto;
import models.Article;
import models.Commande;
import models.Creneau;
import models.Magasin;
import models.Panier;
import models.Stocker;
import repositories.ArticleRepository;
import repositories.MagasinRepository;

/**
 * Servlet implementation class ManagementStock
 */
@WebServlet("/management/stock")
public class ManagementStockServlet extends AbstractServlet {

	private static final long serialVersionUID = 7770262801867382923L;

	MagasinRepository magasinRepository = new MagasinRepository();
	ArticleRepository articleRepository = new ArticleRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Checks
		final List<Magasin> magasins = magasinRepository.findAll();
		request.setAttribute("magasins", magasins);

		Map<Article, Stocker> articles = new HashMap<>();

		final String magasinId = (String) request.getAttribute("magasin-id");
		Magasin magasin = null;

		if (magasinId != null && !"".equals(magasinId)) {
			magasin = magasinRepository.findById(Integer.parseInt(magasinId));
			articles = magasin.getStockers();
		}

		List<Panier> paniersSurDeuxSemaines = new ArrayList<>();
		List<Commande> commandesApprovisionnement = new ArrayList<>();

		if (magasin != null) {
			commandesApprovisionnement = extractCommandes(magasin.getCommandeApprovisionnement());
			paniersSurDeuxSemaines = extractPaniers(magasin.getCreneaux());
		}

		final List<ArticleStockDto> articlesDtos = construireArticleDtos(paniersSurDeuxSemaines,
				commandesApprovisionnement, articles);
		request.setAttribute("articles", articlesDtos);

		view("management/stock", request, response);

	}

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

	private List<ArticleStockDto> construireArticleDtos(final List<Panier> paniersSurDeuxSemaines,
			final List<Commande> commandesApprovisionnement, final Map<Article, Stocker> articles) {
		final List<ArticleStockDto> resultList = new ArrayList<>();

		if (articles != null && !articles.isEmpty()) {
			for (final Entry<Article, Stocker> entry : articles.entrySet()) {
				final Article article = entry.getKey();
				final Stocker stocker = entry.getValue();

			}
		}

		// TODO Changer le mock
		final ArticleStockDto dto = new ArticleStockDto(1, "banane", "banane_bio.webp");
		final Date ajd = new Date();

		for (int i = 0; i < 15; i++) {
			final Date next = Date.from(ajd.toInstant().plus(Duration.ofDays(i)));

			dto.getStocks().put(next, i + 20);

		}

		resultList.add(dto);

		return resultList;
	}

	private List<Panier> extractPaniers(final Set<Creneau> creneaux) {
		final Date now = new Date();
		final Date limit = Date.from(now.toInstant().plus(Duration.ofDays(15)));

		return extractPaniersByPeriod(creneaux, now, limit);
	}

	private List<Panier> extractPaniersByPeriod(final Set<Creneau> creneaux, final Date now, final Date limit) {
		List<Creneau> creneauxSurDeuxSemaines = new ArrayList<>();

		if (creneaux != null) {
			creneauxSurDeuxSemaines = creneaux.stream()
					.filter(creneau -> creneau.getDateCreneau() != null)
					.filter(creneau -> now.before(creneau.getDateCreneau()) && limit.after(creneau.getDateCreneau()))
					.toList();
		}

		return creneauxSurDeuxSemaines.stream()
				.map(Creneau::getPaniers)
				.flatMap(Set::stream)
				.toList();
	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		responseGet(request, response);

	}

}
