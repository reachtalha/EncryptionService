import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Encryption {
	private static byte[] keyValue;
	private static final String ALGO = "AES";

	public static byte[] getKey(String password) { //used to get a key
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] inputBytes = password.getBytes();
			byte[] key = digest.digest(inputBytes);
			keyValue = Arrays.copyOfRange(key, 0, 16);

		} catch (Exception e) {
			//System.out.println(e.getStackTrace());
		}
		return null;
	}

	private static Key generateKey(String password) throws Exception {
		getKey(password); 
		return new SecretKeySpec(keyValue, ALGO); //creates a new key from password
	}

	public static String encrypt(String data, String password) throws Exception { //encrypts text
		Key key = generateKey(password); //creates a key using user entered password
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes());
		//System.out.println("About to return");
		return Base64.getEncoder().encodeToString(encVal); //returns the encrypted text
	}

	public void encryptFile(String inputPath, String outputPath, String password) { //encrypts file
		try {
			String text = FileUtil.readFile(inputPath, "Encryption"); // uses fileutil to read a file
			String encryptedText = encrypt(text, password); // takes the text and encrypts it
			FileUtil.writeFile(encryptedText, outputPath); // uses fileutil to write the encrypted text back to a file
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
