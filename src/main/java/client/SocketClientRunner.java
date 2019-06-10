package client;

import util.SocketMailLogger;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClientRunner {

    public static void main(String[] args) {
    	if(args.length!=2)
		{
			SocketMailLogger.logErrorMessage(	"Usage: java SocketClientRunner <no of requests> <no of threads>");
			System.exit(1);
		}

		SocketMailLogger.logInfoMessage("client starting");
		int numberOfRequests = Integer.parseInt(args[0]);
		int numberOfThreads = Integer.parseInt(args[1]);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfRequests; i++) {
        	String reqId = UUID.randomUUID().toString().concat("-").concat(String.valueOf(i));
            executorService.execute( new RequestSender(reqId) );
        }
    }

}
