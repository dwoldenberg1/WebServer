import java.security.*;

public class PublicTester {
    
    public static void main(String [] args) {
        //System.setSecurityManager(new SuperRestrictive());

        int serverPort = 4000 + (int)(Math.random() * 2000);

        /* Uncomment this if you want to test manually */
        /* serverPort = 4000; */

        MyWebServer server = new MyWebServer(serverPort);
        new Thread(server).start();

        /* Wait for server to start */
        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }


        {
            System.out.println("Trial 1:");
            ClientSimulator sim = new ClientSimulator("localhost", serverPort);
            sim.requestURL("/login");
            System.out.println(sim.getResponse());
        }

        {
            System.out.println("Trial 2:");
            ClientSimulator sim = new ClientSimulator("localhost", serverPort);
            sim.postURL("/auth", "username=test&password=pass");
            System.out.println(sim.getResponse());
        }

        {
            System.out.println("Trial 3:");
            ClientSimulator sim = new ClientSimulator("localhost", serverPort);
            sim.postURL("/auth", "username=test&password=pass2");
            System.out.println(sim.getResponse());
        }

        {
            System.out.println("Trial 4:");
            ClientSimulator sim = new ClientSimulator("localhost", serverPort);
            sim.requestURL("/auth");
            System.out.println(sim.getResponse());
        }

        System.exit(0);

    }
    
}



class SuperRestrictive extends SecurityManager {
    public void checkPermission(Permission perm) throws SecurityException {
        //System.out.println("Checking: " + perm);
        if (perm.getName().endsWith("MyHTTPResponse.class")) return;
        if (perm.getName().endsWith("MyHTTPRequest.class")) return;
        if (perm.getName().endsWith("MyWebServer.class")) return;
        if (perm.getName().endsWith("MyClientThread.class")) return;
        if (perm.getName().endsWith("ClientSimulator.class")) return;

        /* Socket stuff */
        if (perm.toString().contains("resolve")) return;
        if (perm.toString().contains("listen")) return;
        if (perm.toString().contains("networkaddress")) return;
        if (perm.toString().contains("inetaddr")) return;
        if (perm.toString().contains("getProxySelector")) return;
        if (perm.toString().contains("writeFileDescriptor")) return;
        if (perm.toString().contains("readFileDescriptor")) return;
        if (perm.toString().contains("loadLibrary")) return;

		if (perm.toString().contains("/usr/lib/jvm") && perm.toString().contains("read")) return;
		if (perm.toString().contains("java.lang.reflect.ReflectPermission")) return;
        if (perm.toString().contains("exitVM")) return;


        /* General */
        if (perm.getName().equals("accessDeclaredMembers")) return;
        if (perm.getName().contains("getenv.com.apple")) return;
        if (perm.toString().contains("PropertyPermission")) return;
        if (perm.getName().contains("setContextClassLoader")) return;
		if (perm.getName().equals("modifyThreadGroup")) return;
        if (perm.getName().equals("modifyThread")) {
            System.err.println("Your solution has timed out (maybe an infinite loop?)");
            return;
        }
        throw new SecurityException("Unallowed Permission: " + perm);
    }
}