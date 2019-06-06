package server;

import model.SendEmailRequest;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender
{
    //email queue
    //add to queue
    //get fom queu and send
    //email settings
    //validate email address before accept
	Session session;

	final String SENDER_ADDRESS = "chathura@mymail.org";

	public void init()
	{
		Properties properties=new Properties();
		properties.put( "mail.smtp.host","localhost" );
		properties.put( "mail.smtp.port","25" );
		properties.put( "mail.smtp.auth","false" );
		//fill all the information like host name etc.
		session=Session.getDefaultInstance(properties,null);
	}

	public void sendMessage( SendEmailRequest emailRequest) throws MessagingException
	{

		MimeMessage message = compose( session, emailRequest );
		send( message );
		System.out.println("email :"+emailRequest.getRequestId()+" sent");
	}

	private MimeMessage compose(Session session, SendEmailRequest emailRequest) throws MessagingException
	{
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(SENDER_ADDRESS));
		message.addRecipient( Message.RecipientType.TO,
				new InternetAddress(emailRequest.getRecipientAddress()));
		message.setSubject(emailRequest.getSubject());
		message.setText(emailRequest.getMessage());
		return message;
	}

	private void send(MimeMessage mail) throws MessagingException
	{
		Transport.send( mail );
		System.out.println("message sent successfully....");
	}


}
