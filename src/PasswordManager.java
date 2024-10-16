import java.io.File;
import java.io.IOException;

public class PasswordManager {

	public static void addPassword(String id, String username, String password, String encryptionPassword) { 
		String decryptedFile = null;
		String encryptedFile = null;
		File f = new File("passwords.txt");
		if(!(f.exists() && !f.isDirectory())) {
			//System.out.print("Creating file.");
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		//System.out.println("Reading from file.");
		String s = FileUtil.readFile("passwords.txt", "Decryption"); //reads all the passwords
		Decryption d = new Decryption();
		try {
			decryptedFile = d.decrypt(s, encryptionPassword); //decrypts the passwords
		} catch (Exception e) {
			//System.out.println(e);
		}
		String[] entries = decryptedFile.split("\n"); //splits file for finding passwords
		String result = "";
		for (String entry : entries) {
			if(entry.equals("")) continue;
			if(entry.substring(0, entry.indexOf(":= ")).equals(id)) { //looks for id
				entry.substring(entry.indexOf(":=")+ 3); //returns the passwords
			}
			else {
				result = result + entry + "\n";
			}
		}
		decryptedFile = result;
		decryptedFile += id + ":= " + username + ": " + password + "\n"; //adds new passwords
		//System.out.println("Add DecryptedFile: "+decryptedFile);
		Encryption e = new Encryption();
		try {
			encryptedFile = e.encrypt(decryptedFile, encryptionPassword); //encrypts file
		} catch (Exception a) {
			//System.out.println(a);
			//System.out.println("Caught error");
		}
		//System.out.println("Add enkey: "+ encryptionPassword);
		//System.out.println("Add EncrytedFile: "+encryptedFile);
		//System.out.println("Writing to file.");
		FileUtil.writeFile(encryptedFile, "passwords.txt"); //rewrite the file
	}

	public static String getPassword(String id, String encryptionPassword) {
		String decryptedFile = null;
		File f = new File("passwords.txt");
		if(!(f.exists() && !f.isDirectory())) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		String s = FileUtil.readFile("passwords.txt", "Decryption");
		Decryption d = new Decryption();
		try {
			decryptedFile = d.decrypt(s, encryptionPassword);
		} catch (Exception e) {
			//System.out.println(e);
			//System.out.println("Found an error while decrypting getPassword");
		}
		////System.out.println("Get DecryptedFile: "+decryptedFile);
		////System.out.println("Add enkey: "+ encryptionPassword);
		String[] entries = decryptedFile.split("\n"); //splits file for finding passwords
		//System.out.println("EntriesLen: " + entries.length);
		//System.out.println("Entries: "+ entries);
		for (String entry : entries) {
			//System.out.println("Entry: "+entry);
			//System.out.println("Index of: " + entry.indexOf(":= "));
			if(entry.equals("")) continue;
			if(entry.substring(0, entry.indexOf(":= ")).equals(id)) { //looks for id
				return entry.substring(entry.indexOf(":=")+ 3); //returns the passwords
			}
		}
		return "That ID does not exist";
	}
}
