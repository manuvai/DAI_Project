package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import mappers.ArticleMapper;
import models.Article;
import models.Magasin;
import repositories.ArticleRepository;
import repositories.MagasinRepository;

/**
 * Servlet implementation class ManagementServlet
 */
@WebServlet("/management/")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1,
maxFileSize = 1024 * 1024 * 10,
maxRequestSize = 1024 * 1024 * 100
)
public class ManagementServlet extends AbstractServlet {
	private static final String DELIMITER = ",";

	private static final long serialVersionUID = 1L;

	ArticleMapper articleMapper = ArticleMapper.INSTANCE;
	MagasinRepository magasinRepository = new MagasinRepository();
	ArticleRepository articleRepository = new ArticleRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final List<Magasin> magasins = magasinRepository.findAll();
		request.setAttribute("magasins", magasins);

		view("management/index", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		viderErreurs(request);
		final List<String> errors = validate(request);

		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			responseGet(request, response);
			return;
		}

		// Chargement des lignes de produits depuis le fichier
		final Part csvPart = request.getPart("csv");
		final List<List<String>> csvLines = extractLines(csvPart.getInputStream());

		// Transformation des lignes en liste de produits
		final List<Article> articles = transformArticles(csvLines);

		// Ajouter les produits
		articleRepository.createAll(articles);

		// Redirection vers page de gestion
		doGet(request, response);

	}

	/**
	 * Transformation d'une liste des lignes en liste d'articles.
	 *
	 * @param csvLines
	 * @return
	 */
	private List<Article> transformArticles(final List<List<String>> csvLines) {
		return csvLines == null ? new ArrayList<>()
				: csvLines.stream()
						.map(articleMapper::listToArticle).toList();
	}

	/**
	 * Extraction des lignes du fichier csv
	 *
	 * @param inputStream
	 * @return
	 */
	private List<List<String>> extractLines(final InputStream inputStream) {
		final List<List<String>> records = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

			// On saute la première ligne qui contient les entêtes
			String line = bufferedReader.readLine();

			while ((line = bufferedReader.readLine()) != null) {
				final String[] values = line.split(DELIMITER);
				records.add(Arrays.asList(values));
			}

		} catch (final IOException e) {
			e.printStackTrace();
		}

		return records;
	}

	/**
	 * Validation du formulaire avec fichier.
	 *
	 * @param request
	 * @return
	 */
	private List<String> validate(final HttpServletRequest request) {

		final List<String> errors = new ArrayList<>();
		try {
			// vérifier que le magasin a été choisi
			if (request.getParameter("magasin") == null || request.getParameter("magasin").isBlank()) {
				errors.add("Vous devez choisir un magasin avant de continuer");

			} else if (request.getPart("csv") == null || request.getPart("csv").getSize() <= 0) {
				// vérifier que le fichier a été fourni
				errors.add("Vous devez fournir un fichier avant de continuer");

			} else {

				// Vérifier que le magasin existe
				final Magasin magasin = magasinRepository.findById(Integer.parseInt(request.getParameter("magasin")));
				if (magasin == null) {
					errors.add("Ce magasin n'existe pas");
				}
			}
		} catch (IOException | ServletException e) {
			errors.add("Une erreur est survenue, veuillez réessayer plus tard");
		}


		return errors;
	}

}
