import java.io.FileOutputStream;
import java.io.*;
import java.util.*;

public class MyHTTPRequest {

    /* What are the method and url? */
    protected String method   = null;
    protected String url      = null;
    protected String protocol = null;

    private boolean firstLine = true;

    /* Track all of the HTTP headers that are sent */
    protected HashMap<String, String> headers = new HashMap<String, String>();

    protected HashMap<String, String> postData = new HashMap<String, String>();

    /* Use this to track the exact parse error */
    protected String parseError = null;

    /* You may want additional variables */
    /* ::: Any additional variables go here ::: */

    /* Parse an incoming line */
    public void parseRequestLine(String line) {
        /* ::: THIS FUNCTION GETS CALLED FOR EVERY LINE OF THE REQUEST HEADER ::: */
        if (firstLine){
            int space = line.indexOf(' ');
            method = line.substring(0, space);
            line=line.substring(space+1);
            space= line.indexOf(' ');
            url = line.substring(0, space);
            line=line.substring(space+1);
            protocol = line.substring(0);
            firstLine = false;
            return;
        }

        int colon = line.indexOf(':');
        String header = line.substring(0, colon);
        String headerValue = line.substring(colon+1);

        headers.put(header, headerValue);
    }

    public void parseData(String line){
        String[] dataLine= line.split("&");
        for(String keyVal : dataLine) {
            String key = keyVal.substring(0, keyVal.indexOf('='));
            String val = keyVal.substring(keyVal.indexOf('=')+1);
            postData.put(key, val);
        }

    }

    /* Is anything wrong with the request? */
    public String parseError() {
        return this.parseError;
    }
}
