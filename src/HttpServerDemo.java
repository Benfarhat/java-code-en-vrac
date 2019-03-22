import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.validator.routines.DomainValidator;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class HttpServerDemo {
	private static final String DEFAULT_HOSTNAME = "127.0.0.1";
	private static final int DEFAULT_PORT = 8001;
	
	private static final String MODE = "debug";
	private static final String TPL_EXT = ".tpl";
	
	private static String hostname;
	private static int port;
	private static Map<String, HttpHandler> route;
	private static HttpServer server = null;
	private static HttpServerDemo INSTANCE = null;
	
	
	public static void main(String[] args) throws IOException {
		
		initialize(args);
		createHttpServer()
			.bindServer(getInetSocketAddress(hostname, port), 0)
			.getRoute()
			.createContext()
			.start();
	}
	/**
	 * Initialize: Check args, hostname, port and create INSTANCE
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	private static void initialize(String[] args) {
		if (!System.getProperty("java.version").startsWith("10.")) {
		    System.err.println("Please upgrade your java version (at least to Java 10)");
		    System.exit(1);
		}
		if(!(MODE == "debug")) {
			
			if (args.length < 3) {
				System.out.println("Please provide more options.");
				System.out.println(System.getProperty("sun.java.command")
						+ "[hostname] "
						+ "[port]");
				System.exit(0);
			} else {
				hostname = args[0];
				port = Integer.valueOf(args[1]);
			}
			
		} else {
			
			hostname = DEFAULT_HOSTNAME;
			port = DEFAULT_PORT;
			
		}
		
		int attempt = 0;
		int maxAttempt = 10;
		while (!(isPortAvailable(port) && (attempt < maxAttempt))) {
			System.err.println("Port " + port + " is not available, trying with " + ++port);
			
			if (attempt++ == maxAttempt)
				System.exit(0);
		}

		INSTANCE = new HttpServerDemo();
	}

	/**
	 * Test if the provided port is available
	 * 
	 * @param port
	 * @return
	 */
	private static boolean isPortAvailable(int port) {
		
	    try (Socket ignored = new Socket(hostname, port)) {
	        return false;
	    } catch (IOException ignored) {
		    return true;
	    }
	}

	
	/**
	 *  Get Map of context information
	 * @return Map<String, HttpHandler>
	 */
	private HttpServerDemo getRoute(){
		route = new HashMap<String, HttpHandler>();
		route.put("/", HttpServerDemo::handleHomeRequest);
		route.put("/index", HttpServerDemo::handleIndexRequest);
		route.put("/view", HttpServerDemo::handleViewRequest);
		route.put("/about", HttpServerDemo::handleAboutRequest);
		route.put("/demo", HttpServerDemo::handleRequest);
		return INSTANCE;
	}
	
	/**
	 * create a Simple HTTP Server
	 * @return
	 * @throws IOException
	 */
	public static HttpServerDemo createHttpServer() throws IOException {
		try {
			server = HttpServer.create();
		} catch (BindException be) {
			System.err.println("BindException: "
					+ "the server cannot bind to the requested address, "
					+ "or the server is already bound");
			be.printStackTrace();
		}
		return INSTANCE;
	}
	
	/**
	 * prepare InetSocket Address for server binding
	 * @param hostname
	 * @param port
	 * @return
	 */
	private static InetSocketAddress getInetSocketAddress(String hostname, int port) {
		InetSocketAddress isa = null;
		// TCF InetSocketAddress instanciation
		try {
			isa = new InetSocketAddress(hostname, port);
		} catch (IllegalArgumentException iae) {
			System.err.println("IllegalArgumentException: "
					+ "the port " + port + " is outside the range of valid port values "
					+ "or hostname parameter " + hostname + " is null.");
			iae.printStackTrace();
		} catch (SecurityException se) {
			System.err.println("SecurityException: "
					+ se.getMessage());
			se.printStackTrace();
		}
		return isa;
	}
	
	/**
	 * Bind server
	 * 
	 * @param isa
	 * @param backlog
	 * @return
	 */
	private HttpServerDemo bindServer(InetSocketAddress isa, int backlog) {
		// TCF server binding
		try {
			server.bind(isa, backlog);
		} catch (NullPointerException npe) {
			System.err.println("NullPointerException: "
					+ "Address " 
					+ server.getAddress()
					+ " to listen on is null");
			npe.printStackTrace();
		} catch (IOException ioe) {
			System.err.println("IOExcepton: "
					+ ioe.getMessage());
			ioe.printStackTrace();
		}
		return INSTANCE;
	}
	
	/**
	 * Create context from Map
	 * 
	 * @param route
	 * @return
	 */
	private HttpServerDemo createContext() {
		var itRoute = getIteratorRoute(route);
		while(itRoute.hasNext()){
		   Entry<String, HttpHandler> e = itRoute.next();
		   addContext(e.getKey(),e.getValue());
		}
		return INSTANCE;
	}
	
	private static Iterator<Entry<String, HttpHandler>> getIteratorRoute(Map<String, HttpHandler> route) {
		Set<Entry<String, HttpHandler>> setRoute = route.entrySet();
		Iterator<Entry<String, HttpHandler>> itRoute = setRoute.iterator();
		return itRoute;
	}
	
	/**
	 * Add context to server
	 * 
	 * @param path
	 * @param handler
	 */
	private static void addContext(String path, HttpHandler handler) {

		HttpContext context = null;

		try {
			context = server.createContext(path);
			context.setHandler(handler);
		} catch (NullPointerException npe) {
			System.err.println("NullPointerException: "
					+ "Path "
					+ context.getPath()
					+ " or handler "
					+ handler
					+ " is null");
			npe.printStackTrace();
		}
	}
	
	private HttpServerDemo start() {
		server.start();
		System.out.println("\n-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
		System.out.println("Listening at " + hostname + " on port " + port);
		System.out.println("\n-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
		System.out.println("Available context are:");
		var itRoute = getIteratorRoute(route);
		while(itRoute.hasNext()){
		   Entry<String, HttpHandler> e = itRoute.next();
		   switch(port) {
		   case 80: System.out.println("\thttp://" + hostname + e.getKey());
			   break;
		   case 443: System.out.println("\thttps://" + hostname + e.getKey());
			   break;
		   default: System.out.println("\t" + hostname + ":" + port + e.getKey());
		   }
		}
		System.out.println("\n-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
		return INSTANCE;
	}

	public static void handleHomeRequest(HttpExchange exchange) throws IOException {
		render(exchange, "home", null);
	}	
	public static void handleIndexRequest(HttpExchange exchange) throws IOException {
		render(exchange, "index", null);
	}
	public static void handleViewRequest(HttpExchange exchange) throws IOException {
		render(exchange, "view", null);
	}
	public static void handleAboutRequest(HttpExchange exchange) throws IOException {
		render(exchange, "about", null);
	}
	public static void handleRequest(HttpExchange exchange) throws IOException {

		
	}
	
	public static void render(HttpExchange exchange, String template, Object[] datas) throws IOException {
		
		URI requestURI = exchange.getRequestURI();
		System.out.println("Rendering " + template + " for " + requestURI.toString());
		
		String response = getTemplateContent(template);	
		exchange.sendResponseHeaders(200, response.getBytes().length);	
		
		OutputStream output = exchange.getResponseBody();
		output.write(response.getBytes());
		output.close();
	}
	
	private static String getTemplateContent(String fileName) {
		
		StringBuilder content = new StringBuilder();
        String line = null;

        try {
            FileReader fileReader = 
                new FileReader(fileName + TPL_EXT);

            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open template file: " + fileName + "'");                
        }
        catch(IOException ioe) {
            System.out.println("Error reading template file: " + fileName );  
        }
        
        return content.toString();
	}

}
