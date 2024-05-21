package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

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

	private static final List<String> ZIP_TYPES = Arrays.asList("application/x-zip-compressed", "application/zip",
			"multipart/x-zip", "application/zip-compressed", "application/x-zip");

	private static final List<String> CSV_TYPES = Arrays.asList("text/csv");

	private static final List<String> IMAGES_TYPES = Arrays.asList("image/jpeg", "image/png");

	private static final long serialVersionUID = 1L;

	ArticleMapper articleMapper = ArticleMapper.INSTANCE;
	ArticleRepository articleRepository = new ArticleRepository();

	@Override
	protected void responseGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final List<Article> articles = articleRepository.findAll();
		request.setAttribute("articles", articles);
		view("management/index", request, response);

	}

	@Override
	protected void responsePost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		viderSucces(request);
		viderErreurs(request);

		List<Article> articles = new ArrayList<>();
		Map<String, byte[]> images = new HashMap<>();

		for (final Part part : request.getParts()) {
			final String contentType = part.getContentType();

			if (CSV_TYPES.contains(contentType)) {
				final List<List<String>> csvLines = extractLines(part.getInputStream());

				// Transformation des lignes en liste de produits
				articles.addAll(transformArticles(csvLines));

			} else if (IMAGES_TYPES.contains(contentType)) {
				// Récupération du chemin des fichiers temporaires
				final String imageName = ServletUtil.getSubmittedFileName(part);
				images.put(imageName, ServletUtil.toByteArray(part));

			} if (ZIP_TYPES.contains(contentType)) {

				final ZipContent zipContent = ZipContent.extractFromZipPart(part);

				articles.addAll(zipContent.getArticles());
				images.putAll(zipContent.getImages());
			}
		}

		final List<String> errors = validateRequest(images, articles);
		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			responseGet(request, response);
			return;
		}

		// Extraire les articles non présents dans la base.
		articles = filterOnlyNonExistingArticles(articles);

		// Enlever les images des articles enlevés.
		images = filterOnlyNonExistingImages(images, articles);

		// Modifier le chemin d'acces des images d'articles
		for (final Article article : articles) {
			final String nouveauChemin = "images/articles/" + article.getCheminImage();
			article.setCheminImage(nouveauChemin);
		}

		// Uploader les images
		ServletUtil.uploadImages(images, getProperty("projectPath"));

		// Ajouter les produits
		articleRepository.createAll(articles);

		// Ajout d'un message de succès
		ajouterSucces("Produits ajoutés au catalogue", request);

		// Redirection vers page de gestion
		doGet(request, response);
	}

	/**
	 * Filtrage des images en fonction des articles restants.
	 *
	 * @param images
	 * @param articles
	 * @return
	 */
	private Map<String, byte[]> filterOnlyNonExistingImages(final Map<String, byte[]> images,
			final List<Article> articles) {
		final Map<String, byte[]> resultMap = new HashMap<>();

		if (articles != null && images != null) {
			// On créé une liste des images des articles n'existant pas en base
			final List<String> articleImages = articles.stream()
					.map(Article::getCheminImage)
					.toList();
			// On filtre les images qui n'existent pas en base
			final List<String> imagesKeys = images.keySet().stream()
					.filter(articleImages::contains)
					.toList();

			// On n'ajoute que les images des articles n'étant pas en base
			imagesKeys.forEach(image -> resultMap.put(image, images.get(image)));
		}

		return resultMap;
	}

	/**
	 * Filtrage des articles qui n'existent pas en base.
	 *
	 * @param articles
	 * @return
	 */
	private List<Article> filterOnlyNonExistingArticles(final List<Article> articles) {
		final List<Article> resultList = new ArrayList<>();

		if (articles != null && !articles.isEmpty()) {
			for (final Article article : articles) {
				final Article searchedArticle = articleRepository.findByName(article.getLib());

				if (searchedArticle == null) {
					resultList.add(article);
				}
			}
		}

		return resultList;
	}

	/**
	 * Extraction des articles contenus dans le CSV du ZIP.
	 *
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	private static List<Article> extractArticleListFromZippedCsv(final InputStream inputStream)
			throws IOException, CsvValidationException {
		final List<Article> products = new ArrayList<>();
		try {
			@SuppressWarnings("resource")
			final CSVReader reader = new CSVReader(new InputStreamReader(inputStream));

			reader.readNext();

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				products.add(ArticleMapper.INSTANCE.listToArticle(Arrays.asList(nextLine)));
			}
		} catch (final Exception e) {

		}

		return products;
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
	 * @param imagesMap
	 * @param articles
	 * @param request
	 * @return
	 */
	private List<String> validateRequest(final Map<String, byte[]> imagesMap, final List<Article> articles) {

		// Vérifier que les images fournies correspondent et les noms
		final List<String> errors = new ArrayList<>();

		if (imagesMap == null || imagesMap.isEmpty()) {
			errors.add("Vous devez fournir les images des articles");

		} else if (articles == null || articles.isEmpty()) {
			errors.add("Vous devez fournir un fichier CSV avant de continuer");

		} else if (imagesMap.size() != articles.size()) {
			errors.add("Le nombre d'images fourni ne correspond pas ");

		} else {
			final List<String> nomsImages = imagesMap.keySet()
					.stream()
					.map(String::strip)
					.toList();

			final List<String> nomsImagesArticles = articles.stream()
					.map(Article::getCheminImage)
					.map(String::strip)
					.toList();

			final boolean isExact = nomsImages.containsAll(nomsImagesArticles)
					&& nomsImagesArticles.containsAll(nomsImages);

			if (!isExact) {
				errors.add("Les images fournies doivent avoir les mêmes noms que ceux du fichier CSV");
			}

		}


		return errors;
	}

	private static class ZipContent {

		private List<Article> articles = new ArrayList<>();
		private Map<String, byte[]> images = new HashMap<>();

		/**
		 * @return the articles
		 */
		public List<Article> getArticles() {
			return articles;
		}


		/**
		 * @return the images
		 */
		public Map<String, byte[]> getImages() {
			return images;
		}

		/**
		 * Extraction d'un objet contenant les articles et images d'un fichier ZIP.
		 *
		 * @param part
		 * @return
		 * @throws IOException
		 */
		public static ZipContent extractFromZipPart(final Part part) throws IOException {

			ZipContent zipContent = null;

			if (part != null) {
				zipContent = new ZipContent();
				try (final ZipInputStream zis = new ZipInputStream(part.getInputStream())) {
					ZipEntry entry;
					while ((entry = zis.getNextEntry()) != null) {

						if (!entry.isDirectory()) {
							final String entryName = entry.getName();
							final List<String> imageExtensions = Arrays.asList(".jpg", ".jpeg", ".png");

							if (entryName.endsWith(".csv")) {
								zipContent.getArticles().addAll(extractArticleListFromZippedCsv(zis));

							} else if (imageExtensions.stream().anyMatch(ext -> entryName.endsWith(ext))) {
								zipContent.getImages().put(entryName, IOUtils.toByteArray(zis));
							}
						}
					}
				} catch (final CsvValidationException e) {
					zipContent = null;
				}

			}

			return zipContent;
		}

	}
}
