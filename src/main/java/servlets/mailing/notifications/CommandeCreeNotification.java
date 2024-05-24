package servlets.mailing.notifications;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import models.Article;
import models.Composer;
import models.Creneau;
import models.Creneau.HeureCreneau;
import models.Panier;
import models.Utilisateur;

public class CommandeCreeNotification extends AbstractNotification {

	public CommandeCreeNotification(final Utilisateur utilisateur, final Creneau creneau, final Panier panier) {
		super("Votre commande est enregistrée", "");

		computeReceipt(utilisateur);
		computeDateReceipt(creneau);
		computeBasketContent(panier);
	}

	/**
	 * Gestion du contenu du message avec le contenu du panier.
	 *
	 * @param panier
	 */
	private void computeBasketContent(final Panier panier) {

		String message = getMessage();

		message = message.concat("<table style=\"width: 31%; margin-right: calc(69%);\">\r\n"
				+ "    <tbody>\r\n"
				+ "        <tr>\r\n"
				+ "            <td style=\"width: 27.0153%;\">\r\n"
				+ "                <div style=\"text-align: center;\"><span style=\"font-family: Verdana, Geneva, sans-serif;\"><strong>#</strong></span></div>\r\n"
				+ "            </td>\r\n"
				+ "            <td style=\"width: 45.098%;\">\r\n"
				+ "                <div style=\"text-align: center;\"><span style=\"font-family: Verdana, Geneva, sans-serif;\"><strong>Article</strong></span></div>\r\n"
				+ "            </td>\r\n"
				+ "            <td style=\"width: 27.6654%;\">\r\n"
				+ "                <div style=\"text-align: center;\"><span style=\"font-family: Verdana, Geneva, sans-serif;\"><strong>Quantit&eacute;</strong></span></div>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>");

		final Map<Article, Composer> composers = panier.getComposers();

		for (final Entry<Article, Composer> entry : composers.entrySet()) {
			final Article article = entry.getKey();
			final Composer composer = entry.getValue();

			message = message.concat("<tr>\r\n"
					+ "            <td style=\"width: 27.0153%;\">\r\n"
					+ "                <div style=\"text-align: center;\">" + article.getId() + "</div>\r\n"
					+ "            </td>\r\n"
					+ "            <td style=\"width: 45.098%;\">" + article.getLib() + "</td>\r\n"
					+ "            <td style=\"width: 27.6654%;\">\r\n"
					+ "                <div style=\"text-align: center;\">" + composer.getQte() + "</div>\r\n"
					+ "            </td>\r\n"
					+ "        </tr>");
		}

		message = message.concat("\r\n"
				+ "    </tbody>\r\n"
				+ "</table>");

		setMessage(message);

	}

	/**
	 * Gestion du contenu du message avec la date de réception.
	 *
	 * @param creneau
	 */
	private void computeDateReceipt(final Creneau creneau) {
		final Date dateCreneau = creneau.getDateCreneau();
		final HeureCreneau heure = creneau.getHeureCreneau();

		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		String message = getMessage();

		message = message.concat("<p>\r\n"
				+ "    <span style=\"font-family: Verdana, Geneva, sans-serif;\">\r\n"
				+ "        Vous recevrez une notification par mail une fois la commande\r\n"
				+ "        pr&ecirc;te.<br><br>"
				+ "Votre date de retrait est pr&eacute;vue pour le " + format.format(dateCreneau) + " de " + heure.getValue() + ".\r\n"
				+ "        <br>\r\n"
				+ "        <br>\r\n"
				+ "        R&eacute;capitulatif de\r\n"
				+ "        votre commande :<br>\r\n"
				+ "    </span>\r\n"
				+ "</p>");

		setMessage(message);
	}

	/**
	 * Gestion du contenu du message
	 *
	 * @param utilisateur
	 */
	private void computeReceipt(final Utilisateur utilisateur) {
		String message = getMessage();
		message = message.concat("<p>\r\n"
				+ "    <span style=\"font-family: Verdana, Geneva, sans-serif;\">\r\n"
				+ "        Bonjour " + utilisateur.getNom() + ", votre commande a bien &eacute;t&eacute; prise en compte !\r\n"
				+ "    </span>\r\n"
				+ "</p>");

		setMessage(message);

	}

}
