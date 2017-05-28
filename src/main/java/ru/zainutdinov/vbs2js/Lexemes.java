package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;
import ru.zainutdinov.vbs2js.lexeme.Unknown;
import ru.zainutdinov.vbs2js.word.EndFunction;
import ru.zainutdinov.vbs2js.word.EndSub;
import ru.zainutdinov.vbs2js.word.IWord;
import ru.zainutdinov.vbs2js.word.ParenthesisClose;
import ru.zainutdinov.vbs2js.word.ParenthesisOpen;

public class Lexemes {

	private List<ILexeme> lexemes = new ArrayList<ILexeme>();

	private static List<IWord> extractParameters(List<IWord> words) {
		List<IWord> result = new ArrayList<IWord>();

		if (!words.get(0).getClass().equals(ParenthesisOpen.class)) {
			return result;
		}

		IWord word;

		do {
			word = words.remove(0);
			result.add(word);
		}
		while (!word.getClass().equals(ParenthesisClose.class));

		return result;
	}

	private static List<IWord> extractBodySub(List<IWord> words) {
		List<IWord> result = new ArrayList<IWord>();

		IWord word = words.remove(0);

		while (!word.getClass().equals(EndSub.class)) {
			result.add(word);
			word = words.remove(0);
		}

		return result;
	}

	private static List<IWord> extractBodyFunction(List<IWord> words) {
		List<IWord> result = new ArrayList<IWord>();

		IWord word = words.remove(0);

		while (!word.getClass().equals(EndFunction.class)) {
			result.add(word);
			word = words.remove(0);
		}

		return result;
	}

	private static List<ILexeme> parse(List<IWord> words) {
		List<ILexeme> result = new ArrayList<ILexeme>();

		if (words.isEmpty()) {
			return result;
		}

		List<ru.zainutdinov.vbs2js.word.Unknown> unknown = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		IWord word = words.remove(0);

		while (true) {
			Class<?> wordClass = word.getClass();

			if (wordClass.equals(ru.zainutdinov.vbs2js.word.Unknown.class)) {
				unknown.add((ru.zainutdinov.vbs2js.word.Unknown)word);
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Public.class)) {
				//if (!unknown.isEmpty()) {
				//	lexemes.add(new Unknown(unknown));
				//	unknown = "";
				//}

				result.add(new Public());
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Private.class)) {
				//if (!unknown.isEmpty()) {
				//	lexemes.add(new Unknown(unknown));
				//	unknown = "";
				//}

				result.add(new Private());
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Sub.class)) {
//				if (!unknown.isEmpty()) {
//					lexemes.add(new Unknown(unknown));
//					unknown = "";
//				}

				ru.zainutdinov.vbs2js.word.Unknown name = (ru.zainutdinov.vbs2js.word.Unknown)words.remove(0);

				List<IWord> parameters = extractParameters(words);
				List<ILexeme> body = parse(extractBodySub(words));
				result.add(new Sub(name, parameters, body));
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Function.class)) {
				ru.zainutdinov.vbs2js.word.Unknown name = (ru.zainutdinov.vbs2js.word.Unknown)words.remove(0);

				List<IWord> parameters = extractParameters(words);
				List<ILexeme> body = parse(extractBodyFunction(words));
				result.add(new Function(name, parameters, body));
			}
			else {
				// TODO: test for new IWord
			}

			if (words.size() == 0) {
				if (!unknown.isEmpty()) {
					result.add(new Unknown(unknown));
				}

				break;
			}

			word = words.remove(0);
		}

		return result;
	}
	
	public Lexemes(List<IWord> words) {
		lexemes = parse(words);
	}

	public List<ILexeme> getLexemes() {
		return lexemes;
	}
}