package server;

import model.SendEmailRequest;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.concurrent.LinkedBlockingQueue;

public class EmailQueue
{
	private LinkedBlockingQueue<SendEmailRequest> mailQueue;

	public EmailQueue()
	{
		this.mailQueue = new LinkedBlockingQueue<>(500); //mail queue capacity is 500
	}

	public boolean enqueueMail(SendEmailRequest emailRequest) throws InterruptedException
	{
		try
		{
			InternetAddress emailAddress = new InternetAddress(emailRequest.getRecipientAddress());
			emailAddress.validate();
		}
		catch(AddressException e)
		{
			return false;
		}
		//we can prevent duplicates by check if already exists in the queue
		this.mailQueue.put(emailRequest);
		return true;
	}

	public SendEmailRequest dequeueMail() throws InterruptedException
	{
		return mailQueue.take();
	}
}


