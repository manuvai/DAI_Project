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

import models.Article;
import models.Magasin;
import repositories.ArticleRepository;
import repositories.MagasinRepository;

/**
 * Servlet implementation class ManagementServlet
 */
@WebServlet("/management/")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
maxFileSize = 1024 * 1024 * 10, // 10 MB
maxRequestSize = 1024 * 1024 * 100 // 100 MB
		)
public class ManagementServlet extends AbstractServlet {
	private static final String DELIMITER = ",";

	private static final long serialVersionUID = 1L;

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

		final List<String> errors = validate(request);

		if (errors != null && !errors.isEmpty()) {
			errors.forEach(error -> ajouterErreur(error, request));
			forward("management/", request, response);
			return;
		}

		// TODO charger les produits depuis le fichier
		final Part csvPart = request.getPart("csv");
		final List<List<String>> csvLines = extractLines(csvPart.getInputStream());

		// Transformation des lignes en liste de produits
		final List<Article> articles = transformArticles(csvLines);

		// TODO Ajouter les produits
		articles.forEach(articleRepository::create);

	}

	private List<Article> transformArticles(final List<List<String>> csvLines) {
		final List<Article> articles = new ArrayList<>();

		// TODO
		articles.add(null);

		return articles;
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

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				final String[] values = line.split(DELIMITER);
				records.add(Arrays.asList(values));
			}

		} catch (final IOException e) {
			e.printStackTrace();
		}

		return records;
	}

	private List<String> validate(final HttpServletRequest request) {

		final List<String> errors = new ArrayList<>();
		// vérifier que le magasin a été choisi
		if (request.getParameter("magasinId") == null || request.getParameter("magasinId").isBlank()) {
			errors.add("Vous devez choisir un magasin avant de continuer");

		} else {
			try {
				if (request.getPart("csv") == null) {
					// vérifier que le fichier a été fourni
					errors.add("Vous devez fournir un fichier avant de continuer");

				}
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		}


		return errors;
	}

}
