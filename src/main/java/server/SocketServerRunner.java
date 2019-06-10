package server;

import util.SocketMailLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServerRunner
{
	public static void main(String[] args)
	{
		SocketMailLogger.logInfoMessage("Server staring");
		int port = 9999;
		boolean listening = true;
		int noOfMailSenders = 2;
		int noOfRequestHandlers = 5;
		EmailQueue mailQueue = new EmailQueue();

		ExecutorService mailSenderExecutor = Executors.newFixedThreadPool(noOfMailSenders);
		for(int i = 0; i < noOfMailSenders; i++)
		{
			mailSenderExecutor.execute(new EmailSender(mailQueue));
		}

		ExecutorService mailHandlerExecutor = Executors.newFixedThreadPool(noOfRequestHandlers);
		try(ServerSocket serverSocket = new ServerSocket(port))
		{
			SocketMailLogger.logInfoMessage("Waiting for the client request");
			while(listening)
			{
				mailHandlerExecutor.execute(new EmailRequestHandler(serverSocket.accept(), mailQueue));
			}
		}
		catch(IOException e)
		{
			SocketMailLogger.logErrorMessage("Error occurred while listening for a connection: " + e.getMessage(),e);
		}
	}

}
