package server;

import model.SendEmailAck;
import model.SendEmailRequest;
import model.constant.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerRunner {
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("hello server");
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while (true) {
            System.out.println("Waiting for the client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
//            String message = (String) ois.readObject();
            SendEmailRequest sendEmailRequest = ( SendEmailRequest ) ois.readObject();
            System.out.println("Message Received: " + sendEmailRequest.getRequestId());
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
//            oos.writeObject("Hi Client " + message);
            oos.writeObject(new SendEmailAck(sendEmailRequest.getRequestId(), Status.OK  ) );
            //close resources
            ois.close();
            oos.close();
            socket.close();
            //terminate the server if client sends exit request
//            if (message.equalsIgnoreCase("exit")) break;
            if (sendEmailRequest.getMessage().equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
}
