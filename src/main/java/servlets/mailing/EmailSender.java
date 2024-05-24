package servlets.mailing;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import servlets.mailing.notifications.AbstractNotification;

public class EmailSender {

	private String from = "open@shop.com";

	private Properties properties;

	public EmailSender(final Properties inProperties) {
		properties = inProperties;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(final Properties properties) {
		this.properties = properties;
	}

	public void send(final String receipt, final AbstractNotification notification)
			throws AddressException, MessagingException {
		final Authenticator auth = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
						properties.getProperty("mail.smtp.pass"));
			}

		};
		final Session session = Session.getInstance(properties, auth);

		final Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipt));
		msg.setSubject(notification.getSubject());
		msg.setContent(notification.getMessage(), "text/html");

		Transport.send(msg);
	}

}
