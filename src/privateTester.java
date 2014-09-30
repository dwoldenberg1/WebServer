/**
 * Created by davidwoldenberg on 9/30/14.
 */
import java.security.*;

public class privateTester {

    public static void main(String[] args) {
        //System.setSecurityManager(new SuperRestrictive());

        int serverPort = 1234;

        /* Uncomment this if you want to test manually */
        /* serverPort = 4000; */

        MyWebServer server = new MyWebServer(serverPort);
        new Thread(server).start();

        /* Wait for server to start */
        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }
    }
}