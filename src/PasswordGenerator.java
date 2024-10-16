import java.util.HashSet;
import java.util.Random;

public class PasswordGenerator {

	public static String generatePassword(int passwordLength, String seed) {
		String salt = "123"; //salt added to randomized password
		Random rand = new Random();
		int seed2 = rand.nextInt(Integer.MAX_VALUE); //creates a new seed to help randomize password
		String data = seed + seed2 + System.currentTimeMillis();  //makes password unique in any circumstance
		try {
			Encryption e = new Encryption(); 
			String encryptedText = e.encrypt(salt + data, seed); //encrypts the password with the salt
			return encryptedText.substring(0, Math.min(passwordLength, encryptedText.length())); // makes sure code can't break
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return data;
	}

	public static int editDist(String str1, String str2) {
		int m = str1.length();
		int n = str2.length();
		// Create a table to store results of subproblems
		int dp[][] = new int[m + 1][n + 1];

		// Fill d[][] in bottom up manner
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0)
					dp[i][j] = j;
				else if (j == 0)
					dp[i][j] = i;
				else if (str1.charAt(i - 1) == str2.charAt(j - 1))
					dp[i][j] = dp[i - 1][j - 1];

				else
					dp[i][j] = 1 + Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]);
			}
		}

		return dp[m][n];
	}

	public static String passwordStregnth(String password) { // used to test password strength
		String s = FileUtil.readFile("passwordscommon.txt", "Encryption"); //common passwords to make sure user doesn't have a common password
		String[] commonPasswordList = s.split("\n"); //splits it into an array for later use
		//variables 
		double totalScore = 0;
		int hasLowercase = 0;
		int hasUppercase = 0;
		int hasSpecialChar = 0;
		HashSet<Character> setChar = new HashSet<>(); //creates a set for only unique characters
		for (int i = 0; i < password.length(); i++) { // loop for every character
			char charI = password.charAt(i);
			setChar.add(password.charAt(i));
			if (password.charAt(i) < 65 || (charI > 90 && charI < 97)) { //checks for special characters
				hasSpecialChar = 1;
			}
			if (password.charAt(i) > 64 && password.charAt(i) < 91) { //checks for uppercase
				hasUppercase = 1;
			}
			if (password.charAt(i) > 96 && password.charAt(i) < 123) { //checks for lowercase
				hasLowercase = 1;
			}
		}
		int numUnique = setChar.size();
		if (numUnique < password.length() / 2) {
			totalScore -= (password.length() - numUnique); //subtracts points for less unique characters
		}
		totalScore += Math.max(0.7, hasSpecialChar + hasUppercase + hasLowercase) * password.length(); //adds points for special, uppercase, and lowercase characters
		for (String commonp : commonPasswordList) {
			if (editDist(commonp, password) < 4) {
				totalScore -= (30 / (0.5 + editDist(commonp, password))); //loses points if in commonpasswords
				break;
			}
		}
		//returns a final scoring
		if (totalScore < 20) {
			return "Weak";
		} else if (totalScore < 50) {
			return "Medium";
		} else {
			return "Strong";
		}
	}


}
