import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class NetworkDemo01 {

	public static void main(String[] args) {
		
		try {
			System.out.println("-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println("Localhost : " + localhost);
			InetAddress loopback = InetAddress.getLoopbackAddress();
			System.out.println("Loopback : " + loopback);
		} catch (UnknownHostException uhe) {
			System.err.println(uhe.getMessage() + " The local host name could not be resolved into an address.");
		}
		
		
		Scanner input = new Scanner(System.in);
		String host;

		System.out.println("-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
		System.out.print("\nEnter a hostname or an ip address (enter Q or quit to quit):");
		while ( (host = input.next()).length() > 0) {

			displayInfoOnInput(host);

			System.out.println("-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
			System.out.print("\nEnter a hostname or an ip address (enter Q or quit to quit):");
		};
		
		System.out.println("thank you.");
		input.close();
		
	}

	public static void displayInfoOnInput(String host) {
		try {
			InetAddress data = null;
			if (isValidIP(host))
				data = InetAddress.getByAddress(asBytes(host));
			else
				data = InetAddress.getByName(host);

			System.out.println(data);
			System.out.println(data.getHostName());
			System.out.println(data.isSiteLocalAddress());
			System.out.println(data.getHostAddress());
			System.out.println(data.getCanonicalHostName());
			
		} catch (UnknownHostException uhe) {
			System.err.println(uhe.getMessage() + "\nThe local host name could notbe resolved into an address.");
		}
	}
	public final static byte[] asBytes(String ipAddress) {
		byte[] result = new byte[4];
		if (ipAddress == null || ipAddress.length() < 7 || ipAddress.length() > 15)
			throw new IllegalArgumentException ("Bad address format.");
		
		StringTokenizer token = new StringTokenizer(ipAddress, ".");
		if (token.countTokens() != 4)
			throw new IllegalArgumentException ("Bad address format.");
		int position = 0;
		while (token.hasMoreTokens()) {
			String elem = token.nextToken();
			int ipInt = Integer.valueOf(elem);
			if (ipInt < 0 || ipInt > 255)
				throw new NumberFormatException ("Bad address format");
			result[position++] = (byte) ipInt;
		}

		return result;
		
	}	
	
	public final static boolean isValidIP(String ipAddress) {
		if (ipAddress == null || ipAddress.length() < 7 || ipAddress.length() > 15)
			return false;
		
		StringTokenizer token = new StringTokenizer(ipAddress, ".");
		if (token.countTokens() != 4)
			return false;
		
		while (token.hasMoreTokens()) {
			String elem = token.nextToken();
			int ipInt = Integer.valueOf(elem);
			if (ipInt < 0 || ipInt > 255)
				return false;
		}
		return true;
	}
}
