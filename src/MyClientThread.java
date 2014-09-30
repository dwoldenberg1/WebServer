import java.net.*;
import java.io.*;

public class MyClientThread implements Runnable {
    
    /* The client socket and IO we are going to handle in this thread */
    protected Socket         socket;
    protected PrintWriter    out;
    protected BufferedReader in;
    
    public MyClientThread(Socket socket) {
        /* Assign local variable */
        this.socket = socket;
        
        /* Create the I/O variables */
        try {
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in  = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    
    public void run() {

        MyHTTPRequest req = new MyHTTPRequest();

        /* ::: PROCESS THE HTTP REQUEST FROM THIS.IN HERE ::: */
        try {
            String in = this.in.readLine();
            while (!in.equals("")) {
                req.parseRequestLine(in);
                in = this.in.readLine();
            }
        } catch (IOException e) {
                    /* On exception, stop the thread */
            System.out.println("IOException: " + e);
            return;
        }


        MyHTTPResponse resp = null;

        /* ::: CREATE THE RESPONSE AND SENT IT OUT OVER THIS.OUT */
        if (!req.url.equals("/")){
            resp = new MyHTTPResponse(404, "Page not found");
        }
        else if (req.url.equals("/")) {
            resp = new MyHTTPResponse(200, "OK");
            resp.setBody("<b><i>Connection: " + MyWebServer.numConnections + "</i></b>");
        }

        this.out.println(resp.toString());
        try {
            this.socket.close();
        }
        catch (IOException e){
            System.out.println("IOException: " + e);
            System.exit(1);
        }

    }
    
}

