package ru.zainutdinov.vbs2js;

public class StringUtils {

	public static String repeat(String text, int count) {
		String result = new String();
		
		for (int i = 0; i < count; i++) {
			result += text;
		}
		
		return result;
	}
	
}
