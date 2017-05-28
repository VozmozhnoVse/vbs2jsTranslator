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
				result.add(new Public());
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Private.class)) {
				result.add(new Private());
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Return.class)) {
				// TODO
				List<ru.zainutdinov.vbs2js.word.Unknown> return_ = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				return_.add(new ru.zainutdinov.vbs2js.word.Unknown("return "));
				result.add(new Unknown(return_));
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.True.class)) {
				// TODO: test
				if (!unknown.isEmpty()) {
					result.add(new Unknown(new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>(unknown)));
					unknown.clear();
				}

				// TODO
				List<ru.zainutdinov.vbs2js.word.Unknown> true_ = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				true_.add(new ru.zainutdinov.vbs2js.word.Unknown("true"));
				result.add(new Unknown(true_));
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.False.class)) {
				// TODO: test
				if (!unknown.isEmpty()) {
					result.add(new Unknown(new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>(unknown)));
					unknown.clear();
				}

				// TODO
				List<ru.zainutdinov.vbs2js.word.Unknown> false_ = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				false_.add(new ru.zainutdinov.vbs2js.word.Unknown("false"));
				result.add(new Unknown(false_));
			}
			else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Sub.class)) {
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