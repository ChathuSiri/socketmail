package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.SendEmailAck;
import model.SendEmailRequest;
import util.SocketMailLogger;

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
			SocketMailLogger.logInfoMessage("Mail request received id: " + sendEmailRequest.getRequestId());


			boolean status = mailQueue.enqueueMail(sendEmailRequest);

			SendEmailAck emailAck = new SendEmailAck(sendEmailRequest.getRequestId(), status);
			String writeJson = objectMapper.writeValueAsString(emailAck);
			out.println(writeJson);

			SocketMailLogger.logInfoMessage("Ack sent " + emailAck.getRequestId() + " : " + emailAck.getStatus());

			socket.close();
		}
		catch(JsonProcessingException e)
		{
			SocketMailLogger.logErrorMessage("Error occurred while JSON parsing: " + e.getMessage(),e);
		}
		catch(IOException e)
		{
			SocketMailLogger.logErrorMessage("Exception caught when trying to listening the connection in thread: " + e.getMessage(),e);
		}
		catch(InterruptedException e)
		{
			SocketMailLogger.logErrorMessage("EmailRequestHandler interrupted: " + e.getMessage(),e);
			Thread.currentThread().interrupt();
		}
	}
}
