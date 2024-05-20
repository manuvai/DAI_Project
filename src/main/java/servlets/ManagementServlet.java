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
import repositories.ArticleRepository;
import utils.ServletUtil;

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
	ArticleRepository articleRepository = new ArticleRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		view("management/index", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		viderSucces(request);
		viderErreurs(request);
		List<String> errors = validateRequest(request);

		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			responseGet(request, response);
			return;
		}

		// Récupération du chemin des fichiers temporaires
		final List<Part> imagesParts = extractImages(request);

		// Chargement des lignes de produits depuis le fichier
		final Part csvPart = request.getPart("csv");
		final List<List<String>> csvLines = extractLines(csvPart.getInputStream());

		// Transformation des lignes en liste de produits
		final List<Article> articles = transformArticles(csvLines);

		errors = validateImages(imagesParts, articles);
		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			responseGet(request, response);
			return;
		}


		// Uploader les images
		ServletUtil.uploadImages(imagesParts, getServletContext());

		// Ajouter les produits
		articleRepository.createAll(articles);

		// Ajout d'un message de succès
		ajouterSucces("Produits ajoutés au catalogue", request);

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
	 * Extraction des objets Part concernant les images.
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private List<Part> extractImages(final HttpServletRequest request) throws IOException, ServletException {
		return request == null ? new ArrayList<>()
				: request.getParts().stream().filter(p -> "images".equals(p.getName())).toList();
	}

	/**
	 * Validation du formulaire avec fichier.
	 *
	 * @param request
	 * @return
	 */
	private List<String> validateRequest(final HttpServletRequest request) {
		final List<String> errors = new ArrayList<>();
		try {
			// vérifier que des images d'articles aient été fournies
			final List<Part> parts = extractImages(request);
			if (parts.stream().filter(part -> part.getSize() > 0).count() <= 0) {
				errors.add("Vous devez fournir les images des articles");

			} else if (request.getPart("csv") == null || request.getPart("csv").getSize() <= 0) {
				// vérifier que le fichier a été fourni
				errors.add("Vous devez fournir un fichier CSV avant de continuer");
			}

		} catch (IOException | ServletException e) {
			errors.add("Une erreur est survenue, veuillez réessayer plus tard");
		}


		return errors;
	}

	/**
	 * Validation des images à uploader.
	 *
	 * @param imagesParts
	 * @param articles
	 * @param request
	 * @return
	 */
	private List<String> validateImages(final List<Part> imagesParts, final List<Article> articles) {

		// Vérifier que les images fournies correspondent et les noms
		final List<String> errors = new ArrayList<>();

		if (imagesParts != null && articles != null) {
			if (imagesParts.size() != articles.size()) {
				errors.add("Le nombre d'images fourni ne correspond pas ");
			} else {
				final List<String> nomsImages = imagesParts.stream()
						.map(ServletUtil::getSubmittedFileName)
						.toList();

				final List<String> nomsImagesArticles = articles.stream()
						.map(Article::getCheminImage)
						.toList();

				final boolean isExact = nomsImages.containsAll(nomsImagesArticles)
						&& nomsImagesArticles.containsAll(nomsImages);

				if (!isExact) {
					errors.add("Les images fournies doivent avoir les mêmes noms que ceux du fichier CSV");
				}

			}

		}

		return errors;
	}

}
