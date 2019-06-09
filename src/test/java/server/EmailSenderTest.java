//package server;
//
//import model.SendEmailRequest;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import javax.mail.MessagingException;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.*;
//
///**
// * @author chathuram - 6/6/2019
// */
//public class EmailSenderTest
//{
//
//	EmailSender emailSender;
//
//	@Before
//	public void initTest()
//	{
//		emailSender = new EmailSender();
//		System.out.println("email sender init");
//	}
//
//
//	@Test
//	public void sendTest() throws MessagingException
//	{
//		SendEmailRequest emailRequest = new SendEmailRequest(  );
//		emailRequest.setRequestId("req_id_01");
//		emailRequest.setSenderName("Chathura");
//		emailRequest.setRecipientAddress("siripala@gmail.com");
//		emailRequest.setSubject("Greetings");
//		emailRequest.setMessage("Hi siripala,How are you? \nthanks.");
//
//		emailSender.sendMessage(emailRequest);
//	}
//
//	@Test
//	public void emailQueueTest() throws InterruptedException
//	{
//		SendEmailRequest emailRequest = new SendEmailRequest(  );
//		emailRequest.setRequestId("req_id_01");
//		emailRequest.setSenderName("Chathura");
//		emailRequest.setRecipientAddress("siripala@gmail.com");
//		emailRequest.setSubject("Greetings");
//		emailRequest.setMessage("Hi siripala,How are you? \nthanks.");
//
//		emailSender.queueMessage( emailRequest );
//
//		SendEmailRequest emailRequest2 = new SendEmailRequest(  );
//		emailRequest2.setRequestId("req_id_01");
//		emailRequest2.setSenderName("Chathura");
//		emailRequest2.setRecipientAddress("saman@gmail.com");
//		emailRequest2.setSubject("Greetings");
//		emailRequest2.setMessage("Hi Saman Kumara,How are you? \nthanks.");
//
//		emailSender.queueMessage( emailRequest2 );
//
//		ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//		executorService.execute( new Thread( emailSender ) );
//
//		SendEmailRequest emailRequest3 = new SendEmailRequest(  );
//		emailRequest3.setRequestId("req_id_01");
//		emailRequest3.setSenderName("Chathura");
//		emailRequest3.setRecipientAddress("john@gmail.com");
//		emailRequest3.setSubject("Greetings");
//		emailRequest3.setMessage("Hi john,How are you? \nthanks.");
//
//		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//
//		System.out.println("tasks scheduling");
//		executor.schedule(() -> emailSender.queueMessage( emailRequest3 ), 10, TimeUnit.SECONDS );
//		executor.schedule(() -> emailSender.queueMessage( emailRequest3 ), 20, TimeUnit.SECONDS );
//		executor.schedule(() -> emailSender.queueMessage( emailRequest3 ), 30, TimeUnit.SECONDS );
//
//
//		try
//		{
//			Thread.sleep( 60000 );
//		}
//		catch ( InterruptedException e )
//		{
//			e.printStackTrace();
//		}
//	}
//}