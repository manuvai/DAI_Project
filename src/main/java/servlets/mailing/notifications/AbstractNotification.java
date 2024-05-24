package servlets.mailing.notifications;

public abstract class AbstractNotification {

	protected String subject;
	protected String message;

	protected AbstractNotification() {

	}

	protected AbstractNotification(final String subject, final String message) {
		this.subject = subject;
		this.message = message;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

}
