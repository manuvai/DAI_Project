package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class ServletUtil {
	private static final String UPLOAD_DIR = "images" + File.separator + "articles" + File.separator;

	private ServletUtil() {
		throw new IllegalAccessError();
	}

	/**
	 * Transforme un objet Part en tableau binaire.
	 *
	 * @param part
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(final Part part) throws IOException {
		try (InputStream inputStream = part.getInputStream()) {
			return inputStream.readAllBytes();
		}
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
	 * Téléversement des images sous forme binaire.
	 *
	 * @param images
	 * @param servletContext
	 * @throws IOException
	 */
	public static void uploadImages(final Map<String, byte[]> images, final ServletContext servletContext)
			throws IOException {
		final String uploadPath = servletContext.getRealPath("") + File.separator + UPLOAD_DIR;
		final File uploadFile = new File(uploadPath);

		for (final Map.Entry<String, byte[]> entry : images.entrySet()) {
			final File imageFile = new File(uploadFile, entry.getKey());
			try (FileOutputStream fos = new FileOutputStream(imageFile)) {
				fos.write(entry.getValue());
			}
		}
	}
}
