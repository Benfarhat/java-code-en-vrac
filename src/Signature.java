import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;

public class Signature {
	private static java.security.Signature signature;
	private static KeyPair keyPair;

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {

		String[] availableSignatureAlgorithms = {"SHA1withDSA", "SHA1withRSA", "SHA256withRSA"};
		String[] availableKeyPairGeneratorAlgorithms = {"DiffieHellman", "DSA", "RSA"};
		
		signature = java.security.Signature.getInstance(availableSignatureAlgorithms[2]);		
		keyPair = KeyPairGenerator.getInstance(availableKeyPairGeneratorAlgorithms[2]).generateKeyPair();
		
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		
		String messageToSign = "Write once, run anywhere";
		byte[] data = messageToSign.getBytes("UTF-8");
		
		//  -=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-
		// Let's get a digital signature
		// We need here the data and our privateKey
		byte[] digitalSignature = sign(data, privateKey);
		System.out.println(hexString(digitalSignature));
		// 332e314f575d16619ab9b31916c541ea3c3b680a1d85dbcc171935bd1ac6120263
		//61ea1d2ad2213d28510e15d86538abe949d727cf38807c68cb9bc6e43115626b69d
		//5378469d2153e4a6485f6e9ced9fd84dce2b18ccd8c168514756de4a33d27c659f9
		//9a8572cb4736bbd74c8369dc073e2f429ba97e71934f694cea7ca94f522b7b87c8c
		//3aa2a4fcd27acf7ff3616b8fd78344113e1b2f5e92d2f45e32b355dd5f236ac9e72
		//b6fe3582bcf821130d3e622462957b67a7258408dc6355c12a176828ef31ca15f18
		//fc52eb8737706d3577c5b8ce1feb672fc6ba3f9632491a44d57e75245941cd443ce
		//5118442ad74df67a588d7e5c5e2e82fcc
		
		
		//  -=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-
		// Time to verify
		// We send for verification, data, public key and the digital signature
		boolean verif = verify(data, publicKey, digitalSignature);
		System.out.println(verif);
		// True
		
		//  -=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-=:=-
		// For test only, let's say someone change the original data
		data = "Write anywhere, run once".getBytes("UTF-8");
		verif = verify(data, publicKey, digitalSignature);
		System.out.println(verif);
		// False
		
	}

	private static byte[] sign(byte[] data, PrivateKey privateKey)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
		
				SecureRandom secureRandom = new SecureRandom();		
				signature.initSign(privateKey, secureRandom);
				signature.update(data);
				return signature.sign();
	}

	private static boolean verify(byte[] data, PublicKey publicKey, byte[] digitalSignature)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {

				signature.initVerify(publicKey);
				signature.update(data);
				
				return signature.verify(digitalSignature);
	}
	
	private static String hexString(byte[] data) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i<data.length; i++) {
			hexString.append(Integer.toHexString(0xFF & data[i]));
		}
		return hexString.toString();
	}

}
