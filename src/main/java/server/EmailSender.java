package server;

import model.SendEmailRequest;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class EmailSender implements Runnable
{
	private LinkedBlockingQueue<SendEmailRequest> mailQueue;
	private boolean run;
	//email queue
	//add to queue
	//get fom queu and send
	//email settings
	//validate email address before accept
	private Session session;

	final String SENDER_ADDRESS = "chathura@mymail.org";

	public void init()
	{
		run = true;
		mailQueue = new LinkedBlockingQueue<>();
		Properties properties = new Properties();
		properties.put( "mail.smtp.host", "localhost" );
		properties.put( "mail.smtp.port", "25" );
		properties.put( "mail.smtp.auth", "false" );
		//fill all the information like host name etc.
		session = Session.getDefaultInstance( properties, null );
	}

	public void sendMessage( SendEmailRequest emailRequest ) throws MessagingException
	{

		MimeMessage message = compose( session, emailRequest );
		send( message );
		System.out.println( "email :" + emailRequest.getRequestId() + " sent" );
	}

	public boolean queueMessage( SendEmailRequest emailRequest )
	{
		return this.mailQueue.offer( emailRequest );
	}

	private MimeMessage compose( Session session, SendEmailRequest emailRequest ) throws MessagingException
	{
		MimeMessage message = new MimeMessage( session );
		message.setFrom( new InternetAddress( SENDER_ADDRESS ) );
		message.addRecipient( Message.RecipientType.TO,
				new InternetAddress( emailRequest.getRecipientAddress() ) );
		message.setSubject( emailRequest.getSubject() );
		message.setText( emailRequest.getMessage() );
		return message;
	}

	private void send( MimeMessage mail ) throws MessagingException
	{
		Transport.send( mail );
		System.out.println( "message sent successfully...." );
	}

	public void run()
	{
		while ( run )
		{
			try
			{
				SendEmailRequest request = mailQueue.take();
				MimeMessage mail = compose( session, request );
				send( mail );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
			catch ( MessagingException e )
			{
				e.printStackTrace();
			}
		}
	}
}
