package ru.zainutdinov.vbs2js.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public final class FileUtils {
	private FileUtils() {
	}

	public static String readFileToString(String path) {
		String result = "";
	 
		try {
			File file = new File(FileUtils.class.getClassLoader().getResource(path).toURI());

			try (InputStream fin = new FileInputStream(file)) {
				result = convertStreamToString(fin);
			}
		} catch (URISyntaxException | IOException e) {
		}

		return result;
	}

	private static String convertStreamToString(InputStream is) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}

			return sb.toString();
		}
	}
}



 
 
