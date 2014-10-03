import java.net.*;
import java.io.*;

public class ClientSimulator {
    
    /* I/O Variables */
    protected Socket         socket = null;
    protected PrintWriter    out    = null;
    protected BufferedReader in     = null;

    protected String         host   = null;
    protected int            port   = 0;

    public ClientSimulator(String host, int port) {

        this.host = host;
        this.port = port;

        /* This part needs exception handling */
        try {
            /* Create socket */
            this.socket = new Socket(host, port);
            
            /* Create IO */
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException e) {
            /* Exceptions will print and exit */
            
            System.out.println("Unknown host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            System.exit(1);
        }
        
    }


    public void requestURL(String url) {
        this.out.println("GET " + url + " HTTP/1.1");
        this.out.println("Host: " + host);
        this.out.println("");
    }

    public void postURL(String url, String post) {
        this.out.println("POST " + url + " HTTP/1.1");
        this.out.println("Host: " + host);
        this.out.println("Content-Length: " + post.length());
        this.out.println("");
        this.out.println(post);
    }

    public String getResponse() {
        StringBuffer response = new StringBuffer();

        try {
            while (true) {
                String line = this.in.readLine();
                if (line == null) break;
                response.append(line + "\r\n");
            }
        } catch (Exception e) {

        }

        return response.toString();
    }
}
