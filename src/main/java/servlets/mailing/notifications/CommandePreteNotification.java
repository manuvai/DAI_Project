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

public class CommandePreteNotification extends AbstractNotification {

	public CommandePreteNotification(final Utilisateur utilisateur, final Creneau creneau, final Panier panier) {
		super("Votre commande est prête", "");

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

		final String message = getMessage();

		message.concat("<br><table class=\"table\">\r\n"
				+ "    <thead>\r\n"
				+ "        <tr>\r\n"
				+ "            <th>#</th>\r\n"
				+ "            <th>Article</th>\r\n"
				+ "            <th>Qté</th>\r\n"
				+ "        </tr>\r\n"
				+ "    </thead>\r\n"
				+ "    <tbody>");

		final Map<Article, Composer> composers = panier.getComposers();

		for (final Entry<Article, Composer> entry : composers.entrySet()) {
			final Article article = entry.getKey();
			final Composer composer = entry.getValue();

			message.concat("<tr>\r\n"
					+ "            <td scope=\"row\">" + article.getId() + "</td>\r\n"
					+ "            <td>" + article.getLib() + "</td>\r\n"
					+ "            <td>" + composer.getQte() + "</td>\r\n"
					+ "        </tr>");
		}

		message.concat("\r\n"
				+ "    </tbody>\r\n"
				+ "</table>");

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

		final String message = getMessage();

		message.concat("<br>Votre date de retrait prévu est le " + format.format(dateCreneau));
		message.concat(" de " + heure.getValue());

		setMessage(message);
	}

	/**
	 * Gestion du contenu du message
	 *
	 * @param utilisateur
	 */
	private void computeReceipt(final Utilisateur utilisateur) {
		final String message = getMessage();
		message.concat("<br>Bonjour " + utilisateur.getPrenom() + ", votre commande est prête");

		setMessage(message);

	}

}
