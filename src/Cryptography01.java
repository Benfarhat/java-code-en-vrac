import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cryptography01 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		Scanner sc = new Scanner(System.in);
		String message = null;
		String display = null;
		MessageDigest md = null;
		
		System.out.println("Enter the message to encrypt");
		
		message = sc.nextLine();
		
		List<String> algorithms = new ArrayList<String>();
		algorithms.add("MD2");
		algorithms.add("MD5");
		algorithms.add("SHA-1");
		algorithms.add("SHA-256");
		algorithms.add("SHA-384");
		algorithms.add("SHA-512");
		for(String algo: algorithms) {
			
			try {
				md = MessageDigest.getInstance(algo);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			
			md.update(message.getBytes("UTF-8"));
			
			byte[] digest = md.digest();
			
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i<digest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & digest[i]));
			}
			
			display = algo + " digest";
			System.out.printf("%-20s: %s\n", display, hexString.toString());
		}
		
		sc.close();
		

	}

}
