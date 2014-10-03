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
                if (in.equals("\n")) {
                    break;
                }
                req.parseRequestLine(in);
                in = this.in.readLine();

            }
            if (req.method.equals("POST")) {
                in = this.in.readLine();
                req.parseData(in);
            }
        } catch (IOException e) {
                    /* On exception, stop the thread */
            System.out.println("IOException: " + e);
            return;
        }


        MyHTTPResponse resp = null;

        /* ::: CREATE THE RESPONSE AND SENT IT OUT OVER THIS.OUT */
        if (req.url.equals("/") && req.method.equals("GET")) {
            resp = new MyHTTPResponse(200, "OK");
            resp.setBody("<b><i>Connection: " + MyWebServer.numConnections + "</i></b>");
        }
        else if (req.url.equals("/login")){
            resp = new MyHTTPResponse(200, "OK");
            resp.setBody("<html><body><form method=\"post\" action=\"/auth\"><input type=\"text\" name=\"username\"/><input type=\"password\" name=\"password\"/><input type=\"submit\" /></form></body></html>");
        }
        else if (req.url.equals("/auth")){
            if (req.method.equals("GET")) {
                resp = new MyHTTPResponse(302, "Found");
                resp.setHeader("Location", "/login");
            }else if (req.method.equals("POST")) {
                resp = new MyHTTPResponse(200, "OK");
                String username = req.postData.get("username");
                String password = req.postData.get("password");
                if (username.equals("test") && password.equals("pass")){
                    resp.setBody("Good Login!");
                }else {
                    resp.setBody("Bad Login!");
                }
            }
        }
        else if (!req.url.equals("/") && req.method.equals("GET")){
            resp = new MyHTTPResponse(404, "Page not found");
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

