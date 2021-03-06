package server;

import model.SendEmailRequest;
import util.SocketMailLogger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender implements Runnable
{
	private EmailQueue mailQueue;
	private boolean run;
	private Session session;

	private String smtpHost;
	private String smtpPort;
	private String smtpAuth;
	private String mailBox;

	public EmailSender(EmailQueue mailQueue)
	{
		this.mailQueue = mailQueue;

		smtpHost = "localhost";
		smtpPort = "25";
		smtpAuth = "false";
		mailBox = "companymail.net";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.smtp.auth", smtpAuth);

		init(properties);
	}

	public void init(Properties properties)
	{
		run = true;
		session = Session.getDefaultInstance(properties, null);
	}

	public void sendMessage(SendEmailRequest emailRequest) throws MessagingException
	{

		MimeMessage message = compose(session, emailRequest);
		send(message);
		SocketMailLogger.logInfoMessage("email :" + emailRequest.getRequestId() + " sent");
	}

	private MimeMessage compose(Session session, SendEmailRequest emailRequest) throws MessagingException
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(getEmailFromName(emailRequest.getSenderName())));
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(emailRequest.getRecipientAddress()));
		message.setSubject(emailRequest.getSubject());
		message.setText(emailRequest.getMessage());
		return message;
	}

	private String getEmailFromName(String name)
	{
		return name.toLowerCase().concat("@").concat(mailBox);
	}

	private void send(MimeMessage mail) throws MessagingException
	{
		Transport.send(mail);
	}

	public void run()
	{
		while(run)
		{
			try
			{
				//this call blocks thread if queue is empty until it gets queued
				SendEmailRequest request = mailQueue.dequeueMail();
				MimeMessage mail = compose(session, request);
				send(mail);

				SocketMailLogger.logInfoMessage("Email sent: " + request.getRequestId());
			}
			catch(InterruptedException e)
			{
				SocketMailLogger.logErrorMessage("Email sender running interrupted: " + e.getMessage(),e);
				Thread.currentThread().interrupt();
			}
			catch(MessagingException e)
			{
				SocketMailLogger.logErrorMessage("Email sending failed: " + e.getMessage(),e);
			}
		}
	}
}
