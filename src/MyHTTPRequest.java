import java.io.FileOutputStream;
import java.io.*;
import java.util.*;

public class MyHTTPRequest {

    /* What are the method and url? */
    protected String method   = null;
    protected String url      = null;
    protected String protocol = null;

    /* Track all of the HTTP headers that are sent */
    protected HashMap<String, String> headers = new HashMap<String, String>();

    /* Use this to track the exact parse error */
    protected String parseError = null;

    /* You may want additional variables */
    /* ::: Any additional variables go here ::: */

    /* Parse an incoming line */
    public void parseRequestLine(String line) {
        /* ::: THIS FUNCTION GETS CALLED FOR EVERY LINE OF THE REQUEST HEADER ::: */
        if (line.startsWith("GET") || line.startsWith("POST") || line.startsWith("PUT") || line.startsWith("DELETE")){
            int space = line.indexOf(' ');
            method = line.substring(0, space);
            line=line.substring(space+1);
            space= line.indexOf(' ');
            url = line.substring(0, space);
            line=line.substring(space+1);
            protocol = line.substring(0);
            return;
        }

        int colon = line.indexOf(':');
        String header = line.substring(0, colon);
        String headerValue = line.substring(colon+1);

        headers.put(header, headerValue);
    }

    /* Is anything wrong with the request? */
    public String parseError() {
        return this.parseError;
    }
}
