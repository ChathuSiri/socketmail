package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.SendEmailAck;
import model.SendEmailRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class RequestSender implements Runnable
{
	private String host;
	private int port;
	private ObjectMapper objectMapper;
	private String requestId;

	public RequestSender(String requestId)
	{
		this.requestId = requestId;
		host = "localhost";
		port = 9999;
		objectMapper = new ObjectMapper();

	}

	@Override
	public void run()
	{
		try(
				Socket socket = new Socket(host, port);
				PrintWriter out =
						new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()))
		)
		{
			SendEmailRequest emailRequest = createMailRequest();

			String writeJson = objectMapper.writeValueAsString(emailRequest);
			out.println(writeJson);
			System.out.println("Mail request sent : " + emailRequest.getRequestId());

			String readValue = in.readLine();
			SendEmailAck emailAck = objectMapper.readValue(readValue, SendEmailAck.class);
			System.out.println("Ack received " + emailAck.getRequestId() + " : " + emailAck.getStatus());

			Random r = new Random();
			int sleepTime = r.nextInt(450) + 50;
			Thread.sleep(sleepTime);
			//thread sleeps for a time of 50-500 MS until next request
			//in order to make socket communication more realistic

		}
		catch(JsonProcessingException e)
		{
			System.out.println("Error occurred while JSON parsing: " + e.getMessage());
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.out.println("Error occurred in socket connection: " + e.getMessage());
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private SendEmailRequest createMailRequest()
	{
		SendEmailRequest emailRequest = new SendEmailRequest();
		emailRequest.setRequestId(requestId);
		emailRequest.setSenderName("Chathura");
		emailRequest.setRecipientAddress("siripala@gmail.com");
		emailRequest.setSubject(requestId);
		emailRequest.setMessage("Hi Siripala,How are you? \nregards.");

		return emailRequest;
	}
}
