import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtil {

	public static String readFile(String path, String mode) {
		File file = new File(path);
		BufferedReader reader = null;
		String text = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				text = text + line;
				if(mode.equals("Encryption")) {
					text = text + "\n"; //allows text when read to keep the same break points
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public static void writeFile(String text, String path) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(text);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
