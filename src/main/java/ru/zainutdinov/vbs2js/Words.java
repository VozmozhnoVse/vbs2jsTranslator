package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

import ru.zainutdinov.vbs2js.word.Comma;
import ru.zainutdinov.vbs2js.word.Else;
import ru.zainutdinov.vbs2js.word.ElseIf;
import ru.zainutdinov.vbs2js.word.EndFunction;
import ru.zainutdinov.vbs2js.word.EndIf;
import ru.zainutdinov.vbs2js.word.EndSub;
import ru.zainutdinov.vbs2js.word.False;
import ru.zainutdinov.vbs2js.word.Function;
import ru.zainutdinov.vbs2js.word.IWord;
import ru.zainutdinov.vbs2js.word.If;
import ru.zainutdinov.vbs2js.word.ParenthesisClose;
import ru.zainutdinov.vbs2js.word.ParenthesisOpen;
import ru.zainutdinov.vbs2js.word.Private;
import ru.zainutdinov.vbs2js.word.Public;
import ru.zainutdinov.vbs2js.word.Return;
import ru.zainutdinov.vbs2js.word.Sub;
import ru.zainutdinov.vbs2js.word.Then;
import ru.zainutdinov.vbs2js.word.True;
import ru.zainutdinov.vbs2js.word.Unknown;

public class Words {
	private ArrayList<IWord> words = new ArrayList<IWord>();

	private List<String> split(String text) {
		List<String> result = new ArrayList<String>();

		String[] strings = text.split("\\b");

		for (int i = 0; i < strings.length; i++) {
			String string = strings[i].trim();

			if (!string.isEmpty()) {
				result.add(string);
			}
		}

		return result;
	}

	public Words(String vbs) {
		List<String> strings = split(vbs);

		for (int i = 0; i < strings.size(); i++) {
			String string = strings.get(i);
			IWord word = null;

			if (string.equals(",")) {
				word = new Comma();
			} else if (string.equals("(")) {
				word = new ParenthesisOpen();
			} else if (string.equals(")")) {
				word = new ParenthesisClose();
			} else if (string.equalsIgnoreCase("private")) {
				word = new Private();
			} else if (string.equalsIgnoreCase("public")) {
				word = new Public();
			} else if (string.equalsIgnoreCase("sub")) {
				word = new Sub();
			} else if (string.equalsIgnoreCase("function")) {
				word = new Function();
			} else if (string.equalsIgnoreCase("if")) {
				word = new If();
			} else if (string.equalsIgnoreCase("then")) {
				word = new Then();
			} else if (string.equalsIgnoreCase("elseif")) {
				word = new ElseIf();
			} else if (string.equalsIgnoreCase("else")) {
				String nextString = strings.get(i + 1).trim();

				if (nextString.equalsIgnoreCase("if")) {
					word = new ElseIf();
					i++;
				} else {
					word = new Else();
				}
			} else if (string.equalsIgnoreCase("endif")) {
				word = new EndIf();
			} else if (string.equalsIgnoreCase("end")) {
				String nextString = strings.get(i + 1).trim();

				if (nextString.equalsIgnoreCase("sub")) {
					word = new EndSub();
					i++;
				} else if (nextString.equalsIgnoreCase("function")) {
					word = new EndFunction();
					i++;
				} else if (nextString.equalsIgnoreCase("if")) {
					word = new EndIf();
					i++;
				} else {
					word = new Unknown(string);
				}
			} else if (string.equalsIgnoreCase("return")) {
				word = new Return();
			} else if (string.equalsIgnoreCase("false")) {
				word = new False();
			} else if (string.equalsIgnoreCase("true")) {
				word = new True();
			} else {
				word = new Unknown(string);
			}

			words.add(word);
		}
	}

	public IWord cutFirst() {
		if (words.isEmpty()) {
			return null;
		}

		return words.remove(0);
	}

	public boolean checkFirstClass(Class<?> class_) {
		return words.get(0).getClass().equals(class_);
	}

	public boolean isEmpty() {
		return words.isEmpty();
	}

	public Words cutToExclusive(Class<?> class_) {
		Words result = new Words("");

		IWord word = cutFirst();

		while (!word.getClass().equals(class_)) {
			result.words.add(word);
			word = cutFirst();
		}

		return result;
	}

	public Words cutToInclusive(Class<?> class_) {
		Words result = new Words("");
		IWord word;

		do {
			word = cutFirst();
			result.words.add(word);
		} while (!word.getClass().equals(class_));

		return result;
	}

	public String js() {
		String result = "";

		for (IWord word : words) {
			result += word.js();
		}

		return result;
	}

	public void clear() {
		words.clear();
	}

	public void add(IWord word) {
		words.add(word);
	}
}
