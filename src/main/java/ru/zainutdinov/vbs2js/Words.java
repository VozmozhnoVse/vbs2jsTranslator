package ru.zainutdinov.vbs2js;

import java.util.ArrayList;

public class Words {
	ArrayList<String> words = new ArrayList<String>();

	public Words(String text) {
		String[] strings = text.split("\\b");
		
		for (int i = 0; i < strings.length; i++) {
			String word = strings[i].trim();

			if (!word.isEmpty()) {
				if (word.equals(",")) {
					word += " ";
				}
				else if (word.equals("=")) {
					word = " " + word + " ";
				}
				words.add(word);
			}
		}
	}

	public String cutFirst() {
		if (words.size() == 0) {
			return null;
		}

		String word = words.get(0);
		words.remove(0);
		return word;
	}

	public boolean nextIs(String string) {
		if (words.size() == 0) {
			return false;
		}

		return words.get(0).equals(string);
	}
}
