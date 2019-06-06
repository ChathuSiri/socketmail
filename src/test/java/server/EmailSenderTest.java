package server;

import model.SendEmailRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

/**
 * @author chathuram - 6/6/2019
 */
public class EmailSenderTest
{

	EmailSender emailSender;

	@Before
	public void initTest()
	{
		emailSender = new EmailSender();
		emailSender.init();
	}


	@Test
	public void send() throws MessagingException
	{
		SendEmailRequest emailRequest = new SendEmailRequest(  );
		emailRequest.setRequestId("req_id_01");
		emailRequest.setSenderName("Chathura");
		emailRequest.setRecipientAddress("siripala@gmail.com");
		emailRequest.setSubject("Greetings");
		emailRequest.setMessage("Hi siripala,How are you? \n thanks.");

		emailSender.sendMessage(emailRequest);
	}
}