import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CipherDemo {

	public static void main(String[] args) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

		String[] availableKeyPairGeneratorAlgorithms = {
				"DiffieHellman", 
				"DSA", 
				"RSA"
				};
		String[] availableCipherAlgModPad = {
				"AES/CBC/NoPadding",
				"AES/CBC/PKCS5Padding",
				"AES/ECB/NoPadding",
				"AES/ECB/PKCS5Padding",
				"DES/CBC/NoPadding",
				"DES/CBC/PKCS5Padding",
				"DES/ECB/NoPadding",
				"DES/ECB/PKCS5Padding",
				"DESede/CBC/NoPadding",
				"DESede/CBC/PKCS5Padding",
				"DESede/ECB/NoPadding",
				"DESede/ECB/PKCS5Padding",
				"RSA/ECB/PKCS1Padding",
				"RSA/ECB/OAEPWithSHA-1AndMGF1Padding",
				"RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
				};
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(availableKeyPairGeneratorAlgorithms[2]);
		keyPairGen.initialize(2048);

		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		
		// algorithm/mode/padding
		Cipher cipher = Cipher.getInstance(availableCipherAlgModPad[12]);
		
		// REMENBER!! We crypt with the public key and we decrypt with the private key
		// mean, only the recipient can read the original message with his private key
		
		// ENCRYPTION
		
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		String messageToSign = "Write once, run anywhere";
		byte[] input = messageToSign.getBytes("UTF-8");
		System.out.println("Original text : " + messageToSign + "\n");
		
		cipher.update(input);
		byte[] cipherText = cipher.doFinal();
		
		System.out.printf("Cipher text : %.10s...\n\n", new String(cipherText, "UTF8"));
		
		
		// DECRYPTION
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		byte[] decipheredText = cipher.doFinal(cipherText);
		
		System.out.println(new String(decipheredText) + " <-> " + messageToSign + "\n");
		
		
		// Let's try to use another privateKey (mean someone try to read the message of someone else)
		KeyPairGenerator anotherKeyPairGen = KeyPairGenerator.getInstance(availableKeyPairGeneratorAlgorithms[2]);
		anotherKeyPairGen.initialize(2048);
		KeyPair anotherKeyPair = anotherKeyPairGen.generateKeyPair();
		PrivateKey anotherPrivateKey = anotherKeyPair.getPrivate();
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, anotherPrivateKey);
			decipheredText = cipher.doFinal(cipherText);
			System.out.println(new String(decipheredText) + " <-> " + messageToSign);
		} catch (IllegalStateException ise) {
			System.err.println("cipher is in a wrong state (e.g., has not been initialized) : " + ise.getMessage());
		} catch (IllegalBlockSizeException ibse) {
			System.err.println("cipher is a block cipher, no padding has been requested (only in encryption mode),"
					+ "and the total input length of the data processed by this cipher is not a multiple of block size; "
					+ " or this encryption algorithm is unable to process the input data provided: " + ibse.getMessage());
		} catch (AEADBadTagException abte) {
			System.err.println("cipher is decrypting in an AEAD mode (such as GCM/CCM), and the received authentication "
					+ "tag does not match the calculated value: " + abte.getMessage());
		} catch (BadPaddingException bpe) {
			System.err.println("cipher is in decryption mode, and (un)padding has been requested, "
					+ "but the decrypted data is not bounded by the appropriate padding bytes: " + bpe.getMessage());
		}
		
		

	}

}
