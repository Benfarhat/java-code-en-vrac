import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;

public class MacGeneration {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		KeyGenerator keygen = null;
		String msg = new String("Write once, run anywhere");
	    System.out.println("String to compute : " + msg);
	    
	    List<String> algorithms = new ArrayList<String>();
	    algorithms.add("AES");
	    algorithms.add("DES");
	    algorithms.add("DESede");
	    algorithms.add("HmacSHA1");
	    algorithms.add("HmacSHA256");
	    
	    List<String> algorithmsMAC = new ArrayList<String>();
	    algorithmsMAC.add("HmacMD5");
	    algorithmsMAC.add("HmacSHA1");
	    algorithmsMAC.add("HmacSHA256");
	    
	    for (String algo: algorithms) {
	    	keygen = KeyGenerator.getInstance(algo);
	    	SecureRandom secRandom = new SecureRandom();
	    	keygen.init(secRandom);
	    	Key key = keygen.generateKey();	

	    	for (String algoMac: algorithmsMAC) {

	    		Mac mac = Mac.getInstance(algoMac);
	    		mac.init(key);
	    		
	    		byte[] bytes = msg.getBytes();      
	    		byte[] macResult = mac.doFinal(bytes);

	    		System.out.printf("%-48s: %s", "Result for " + algo + " keygen and " + algoMac + " MAC", new String(macResult));
	    		System.out.println(new String(macResult)); 
	    		
	    	}
	    	
	    }
		
	}
}
