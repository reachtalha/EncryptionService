import java.util.Arrays;
import javax.crypto.Cipher;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Decryption {
	private static final String ALGO = "AES";
	private static byte[] keyValue;

	public static byte[] getKey(String password) {
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

	public static String decrypt(String encryptedData, String password) throws Exception {
		Key key = generateKey(password);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		return new String(decValue);
	}

	private static Key generateKey(String password) throws Exception {
		getKey(password);
		return new SecretKeySpec(keyValue, ALGO);
	}

	public void decryptFile(String inputPath, String outputPath, String password) {
		try {
			String text = FileUtil.readFile(inputPath, "Decryption");
			String decryptedText = decrypt(text, password);
			FileUtil.writeFile(decryptedText, outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
