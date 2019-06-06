package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClientRunner {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        System.out.println("hello client");
        //get the localhost IP address, if server is running on some other IP, you need to use that
//        InetAddress host = InetAddress.getLocalHost();
//        Socket socket = null;
//        ObjectOutputStream oos = null;
//        ObjectInputStream ois = null;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        RequestSender requestSender= new RequestSender();
        System.out.println("hello client");
        for (int i = 0; i < 1; i++) {
//            //establish socket connection to server
//            socket = new Socket(host.getHostName(), 9876);
//            //write to socket using ObjectOutputStream
//            oos = new ObjectOutputStream(socket.getOutputStream());
//            System.out.println("Sending request to Socket Server");
//            if (i == 4) oos.writeObject("bexit");
//            else oos.writeObject("" + i);
//            //read the server response message
//            ois = new ObjectInputStream(socket.getInputStream());
//            String message = (String) ois.readObject();
//            System.out.println("Message: " + message);
//            //close resources
//            ois.close();
//            oos.close();

            System.out.println("executer run");
            executorService.execute( new Thread( requestSender ) );

            Thread.sleep(1000);
        }
    }

}
