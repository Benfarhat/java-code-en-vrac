import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class SocketWhoisDemo {
    
    private static final String WHOIS_HOST    = "whois.internic.net";
    private static final String WHOIS_IP_HOST = "whois.arin.net";
    private static final int    PORT    = 43;
    private static final int    TIMEOUT = 30 * 1000;
    
    private static String host = WHOIS_HOST;
    private static int port = PORT;
    private static int timeout = TIMEOUT;  
    
    private static String defaultURLToCheck = "java.net";
    
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        PrintWriter output = null;
        BufferedReader input = null;
        
        Boolean debug = false;
        
        String domainToCheck = (args.length > 0) ? args[0] : defaultURLToCheck;
        domainToCheck = removeProtocolFromURL(domainToCheck);
        if ( addrIsAnIP( domainToCheck ))
            host = WHOIS_IP_HOST;
         
        try {
            socket = new Socket( host, port );
            socket.setSoTimeout(timeout);
            socket.setKeepAlive(true);
            if ( debug )
                socketInfo(socket);
            
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request = domainToCheck;
            
            output.println( request );
            
            String lineResponse = "";
            
            while ( ( lineResponse = input.readLine() ) != null ) {
                // We don't want to print unused data
                if ( lineResponse.indexOf("   ") == 0 )
                    System.out.println(lineResponse);
            }
            
        } catch(SocketTimeoutException ste) {
            System.out.println("SocketTimeoutException: " + host + " can be reached in " + timeout);
            if ( debug )
                ste.printStackTrace();
            //System.exit(1);
        }  catch(UnknownHostException uhe) {
            System.out.println("UnknownHostException: " + host + " unknown.");
            if ( debug )
                uhe.printStackTrace();
            //System.exit(1);
        } catch(SocketException se) {
            System.out.println("SocketException detected: ");
            if ( debug )
                se.printStackTrace();
        } catch(IOException ioe) {
            System.out.println("IOException detected: ");
            if ( debug )
                ioe.printStackTrace();
        } finally {
            if ( output != null )
                output.close();
            if ( input != null )
                input.close();
            if ( !socket.isClosed() )
                socket.close();
        }

    }
    
    /**
     * Only remove for example http:// or https:// from address
     * 
     * We do not manage every possible case, like:
     *      mailto:john.doe@mail.com
     *      www.example.com:8080/test
     *      news:fr.comp.infosystemes.www.auteurs
     *  
     * @param addr
     * @return
     */
    public static String removeProtocolFromURL(String addr) {        
        int positionStart = ( addr.indexOf( "://" ) == -1 ) ? 0 : addr.indexOf( "://" ) + 3;
        int positionEnd = ( addr.indexOf( '?' ) == -1 ) ? addr.length() : addr.indexOf( '?' );
        return addr.substring(positionStart, positionEnd).toLowerCase();
    }
    
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean addrIsAnIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    
    /**
     * socketInfo 
     * @see Code get from https://alvinalexander.com/blog/post/java/method-debug-java-socket-connection-client-server
     * @param socket
     */
    public static void socketInfo(Socket socket)
    {
        try
        {
          System.out.format("Port:                 %s\n",   socket.getPort());
          System.out.format("Canonical Host Name:  %s\n",   socket.getInetAddress().getCanonicalHostName());
          System.out.format("Host Address:         %s\n\n", socket.getInetAddress().getHostAddress());
          System.out.format("Local Address:        %s\n",   socket.getLocalAddress());
          System.out.format("Local Port:           %s\n",   socket.getLocalPort());
          System.out.format("Local Socket Address: %s\n\n", socket.getLocalSocketAddress());
          System.out.format("Receive Buffer Size:  %s\n",   socket.getReceiveBufferSize());
          System.out.format("Send Buffer Size:     %s\n\n", socket.getSendBufferSize());
          System.out.format("Keep-Alive:           %s\n",   socket.getKeepAlive());
          System.out.format("SO Timeout:           %s\n",   socket.getSoTimeout());
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }

}
