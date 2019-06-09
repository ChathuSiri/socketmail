package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.SendEmailAck;
import model.SendEmailRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EmailRequestHandler implements Runnable
{

	private Socket socket;
	private ObjectMapper objectMapper;
	private EmailQueue mailQueue;

	public EmailRequestHandler(Socket socket, EmailQueue mailQueue )
	{
		this.mailQueue = mailQueue;
		this.socket = socket;
		objectMapper = new ObjectMapper();
	}

	@Override
	public void run()
	{

		try(
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				PrintWriter out =
						new PrintWriter(socket.getOutputStream(), true);
		)
		{
			String inputString = in.readLine();
			SendEmailRequest sendEmailRequest = objectMapper.readValue(inputString, SendEmailRequest.class);
			System.out.println("Mail request received id: " + sendEmailRequest.getRequestId());


			boolean status = mailQueue.enqueueMail(sendEmailRequest);

			SendEmailAck emailAck = new SendEmailAck(sendEmailRequest.getRequestId(), status);
			String writeJson = objectMapper.writeValueAsString(emailAck);
			out.println(writeJson);

			System.out.println("Ack sent " + emailAck.getRequestId() + " : " + emailAck.getStatus());

			socket.close();
		}
		catch(JsonProcessingException e)
		{
			System.out.println("Error occurred while JSON parsing: " + e.getMessage());
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			System.out.println("EmailRequestHandler interrupted: " + e.getMessage());
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		catch(IOException e)
		{
			System.out.println("Exception caught when trying to listening the connection in thread: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private boolean validateMailRequest(SendEmailRequest sendEmailRequest)
	{

		return true;
	}
}
