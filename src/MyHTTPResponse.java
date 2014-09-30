import java.util.*;

public class MyHTTPResponse {

    protected int responseCode                = 0;
    protected String responseStatus           = null;
    protected HashMap<String, String> headers = new HashMap<String, String>();
    protected String body                     = null;

    public MyHTTPResponse(int code, String statusText) {
        /* ::: INITIALIZE MEMBER VARIABLES ::: */
        responseCode = code;
        responseStatus = statusText;
        body="";
    }

    public void setHeader(String header, String value) {
        /* ::: PUT HEADER IN HASHMAP ::: */

        headers.put(header, value);
    }

    public void setBody(String body) {
        /* ::: SET THE RESPONSE BODY; ALSO A GOOD PLACE TO SET THE Content-Length HEADER ::: */

        headers.put("Content-Length", body.length()+"");
        this.body = body;
    }

    public String toString() {
        /* ::: CONVERT THE RESPONSE INTO AN HTTP MESSAGE ::: */

        String response = "HTTP/1.1 " + responseCode + " " + responseStatus + "\n";

        for( String key : headers.keySet() )
        {
                response+= key + ": " + headers.get(key) + "\n";
        }

        response+="\n";
        response+=body+"";
        return response;
    }
}
