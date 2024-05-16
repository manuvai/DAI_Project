package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class ServletUtil {
	private static final String UPLOAD_DIR = "";

	private ServletUtil() {
		throw new IllegalAccessError();
	}

	/**
	 * Récupération du nom du fichier.
	 *
	 * @param part
	 * @return
	 */
	public static String getSubmittedFileName(final Part part) {

		for (final String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				final String fileName = contentDisposition.substring(contentDisposition.indexOf('=') + 1)
						.trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);

			}
		}
		return null;
	}

	/**
	 * Téléversement des images
	 *
	 * @param imagesParts
	 */
	public static void uploadImages(final List<Part> imagesParts, final ServletContext servletContext) {
		// Upload images
		if (imagesParts == null) {
			return;
		}

		final String uploadPath = servletContext.getRealPath("") + File.separator + UPLOAD_DIR;
		final File uploads = new File(uploadPath);

		imagesParts.forEach(part -> {
			final File file = new File(uploads, getSubmittedFileName(part));
			try (InputStream input = part.getInputStream()) {
				Files.copy(input, file.toPath());

			} catch (final IOException e) {
				e.printStackTrace();
			}
		});
	}
}
