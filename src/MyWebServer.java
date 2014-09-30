import java.net.*;
import java.io.*;

public class MyWebServer implements Runnable {

    public static int numConnections = 0;

    private int myPort;

    public MyWebServer(int port) {
        /* Construct the server thread for a specific port */
        this.myPort = port;
    }

    public void run() {

        /* This is the server socket to accept connections */
        ServerSocket serverSocket = null;

        /* Create the server socket */
        try {
            serverSocket = new ServerSocket(this.myPort);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            System.exit(1);
        }

        /* In the main thread, continuously listen for new clients and spin off threads for them. */
        while (true) {
            try {
                /* Get a new client */
                Socket clientSocket = serverSocket.accept();

                /* Increment connections */
                MyWebServer.numConnections++;

                /* Create a thread for it and start! */
                MyClientThread clientThread = new MyClientThread(clientSocket);
                new Thread(clientThread).start();

            } catch (IOException e) {
                System.out.println("Accept failed: " + e);
                System.exit(1);
            }
        }
    }
    
}
