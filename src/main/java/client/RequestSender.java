package client;

import model.SendEmailAck;
import model.SendEmailRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author chathuram - 6/6/2019
 */
public class RequestSender implements Runnable
{
	InetAddress host;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public RequestSender() throws IOException
	{

	}

	@Override
	public void run()
	{
		System.out.println("thread running");
		try
		{
			host = InetAddress.getLocalHost();
			socket = new Socket( host.getHostName(), 9876 );
			//write to socket using ObjectOutputStream
			oos = new ObjectOutputStream( socket.getOutputStream() );
			SendEmailRequest emailRequest = new SendEmailRequest();
			emailRequest.setRequestId( "req_id_01" );
			emailRequest.setSenderName( "Chathura" );
			emailRequest.setRecipientAddress( "siripala@gmail.com" );
			emailRequest.setSubject( "Greetings" );
			emailRequest.setMessage( "Hi siripala,How are you? \nthanks." );

			oos.writeObject( emailRequest );
			System.out.println("mail sent");
			ois = new ObjectInputStream( socket.getInputStream() );
			SendEmailAck ack = ( SendEmailAck ) ois.readObject();
			System.out.println( ack.getRequestId() + " : " + ack.getStatus() );

			ois.close();
			oos.close();
		}
		catch ( IOException | ClassNotFoundException e )
		{
			e.printStackTrace();
		}
	}
}
