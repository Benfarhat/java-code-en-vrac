import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * We 'll use this to share information between thread
 * 
 * A cleanest way to do this, is to use a Consumer/Producer pattern with SynchronousQueue
 * 
 * @author PC
 *
 */
class Shared {
	private static int port;
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		Shared.port = port;
	}
	
}
public class NetworkDemo02 {

	public static void main(String[] args) {
		Shared.setPort(1450);
		new Thread(){
		   public void run(){
			   new Server("S1", Shared.getPort()).start();
		  }
		}.start();
		
//		new Thread(){
//		   public void run(){
//			   new Server("S2", canal2).start();
//		  }
//		}.start();
//		
		new Thread(){
		   public void run(){
			   Client c11 = new Client("C11", Shared.getPort()).connect();
//				Client c12 = new Client("C12", canal1).connect();
//				Client c21 = new Client("C21", canal2).connect();
//				Client c22 = new Client("C22", canal2).connect();
				
				c11.sendMessage("bonjour");
//				c12.sendMessage("hello");
//				c21.sendMessage("صباح الخير");
//				c22.sendMessage("Buongiorno");
				
				c11.sendMessage("Je suis heureux de faire ta connaissance");
//				c12.sendMessage("I'm happy to meet you");
//				c21.sendMessage("انا سعيد بلقائك");
//				c22.sendMessage("Buongiorno");
				
				//c11.disconnect();
		  }
		}.start();
		
	}

}


/**
 * Class SingleServer which will handle Client connection on custom port
 * 
 * @author PC
 *
 */
class Server {
	private ServerSocket server;
	private String name;
	private int port;
	private int backlog = 100;
	
	Server(String name, int port) {
		this.name = name;
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public Server start() {
		int attempt = 10;
		int count = 0;
		while (!connect() && count++ < attempt) {
			port++;
			Shared.setPort(port);
			System.err.println("* [" + name + "] Incementation of number port to " + port);
		}
		
		do {
			handleClient();
		} while(true);
	}
	
	private boolean connect() {
		try {
			server = new ServerSocket(port, backlog, InetAddress.getLocalHost());
			System.err.println("* [" + name + "] listening on port " + port);
			return true;
		} catch (IOException e) {
			System.err.println("* [" + name + "] Unable to attach port " + port);
			return false;
		}
	}
	
	public void handleClient() {
		Socket link = null;
		Scanner input = null;
		PrintWriter output = null;
		
		try {
			link = server.accept();
			
			input = new Scanner(link.getInputStream());
			output = new PrintWriter(link.getOutputStream(), true);
			int numMsg = 0;
			String message = input.nextLine();
			
			while(!message.equals("quit")) {
				System.out.println("[" + name + "] Message received.");
				numMsg++;
				output.println("[" + name + "] Message " + numMsg + ": " + message);
				message = input.nextLine();
			}
			
			System.out.println("[" + name + "] " + numMsg + " messages received.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			
			input.close();
			output.close();
			
			try {
				System.out.println("\n* Closing connection...");
				link.close();
				
			} catch(IOException ioe) {
				System.err.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}


/**
 * Class client
 * 
 * @author PC
 *
 */
class Client {
	
	private InetAddress host;
	public String name;
	private int port;
	Socket link;
	Scanner input;
	PrintWriter output;
	
	public Client(String name, int port) {
		this.name = name;
		this.port = port;
		try {
			this.host = InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) {
			System.err.println("[" + name + "] Host ID not found");
			System.err.println(uhe.getMessage());
		}
	}
	
	public boolean isConnected() {
		if (link == null)
			return false;
		return link.isConnected();
	}
	public Client connect() {
		Socket link = null;
		try {
			link = new Socket(host, port);
			input = new Scanner(link.getInputStream());
			output = new PrintWriter(link.getOutputStream(), true);		
		} catch(IllegalArgumentException iae) {
			System.err.println("[" + name + "] The port parameter is outside the specified range of valid port values," 
					+"which is between0 and 65535, inclusive.");
			System.err.println(iae.getMessage());
		} catch(NullPointerException npe) {
			System.err.println("[" + name + "] Address is null");
			System.err.println(npe.getMessage());
		} catch(SecurityException se) {
			System.err.println("[" + name + "] A security manager exists and its checkConnect method doesn't allow the operation.");
			System.err.println(se.getMessage());
		} catch(IOException ioe) {
			System.err.println("[" + name + "] An I/O error occurs when creating the socket");
		}
		return this;
		
	}
	
	public void disconnect() {
		if (!link.isClosed()) {

			if (input!=null)
				input.close();
			if (output!= null)
				output.close();
			
			try {
				link.close();
			} catch (IOException ioe) {
				System.err.println("[" + name + "] An I/O error occurs when closing this socket.");
				System.err.println(ioe);
			}
		}
	}
	
	public void sendMessage(String message) {
		if(this.isConnected()) {
			output.println("[" + name + "] " + message);
		}
	}
}



/**
 * Class SingleServer which will handle Client connection on custom port
 * 
 * Thread safe and singleton by instancing server with holder class
 * 
 * @author PC
 *
 */
class SingleServer {
	private static ServerSocket server;

	private SingleServer() {
	}
	
	private static class SingletonServerHolder {
		private final static SingleServer instance = new SingleServer();
	}
	
	public static SingleServer getInstance() {
		return SingletonServerHolder.instance;
	}
	
	public static void listen(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Unable to attach port " + port);
			System.exit(1);
		}
		do {
			handleClient();
		} while(true);
	}
	
	public static void handleClient() {
		Socket link = null;
		Scanner input = null;
		PrintWriter output = null;
		
		try {
			link = server.accept();
			
			input = new Scanner(link.getInputStream());
			output = new PrintWriter(link.getOutputStream(), true);
			int numMsg = 0;
			String message = input.nextLine();
			
			while(!message.equals("quit")) {
				System.out.println("Message received.");
				numMsg++;
				output.println("Message " + numMsg + ": " + message);
				message = input.nextLine();
			}
			
			System.out.println(numMsg + " messages received.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			
			input.close();
			output.close();
			
			try {
				System.out.println("\n* Closing connection...");
				link.close();
				
			} catch(IOException ioe) {
				System.err.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}
