package ru.zainutdinov.vbs2js.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class FileUtilities {

	private static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		reader.close();
		return sb.toString();
	}

	public static String readFileToString(String filePath) {
		String ret = new String();

		try {
			File fl = new File(new FileUtilities().getClass().getClassLoader().getResource(filePath).toURI());
			FileInputStream fin = new FileInputStream(fl);
			ret = convertStreamToString(fin);
			fin.close();
		} catch (URISyntaxException e) {
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}

		return ret;
	}
}
