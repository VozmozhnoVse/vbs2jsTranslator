package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.lexeme.If;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;
import ru.zainutdinov.vbs2js.lexeme.Unknown;
import ru.zainutdinov.vbs2js.word.IWord;

public class Lexemes {

	private List<ILexeme> lexemes = new ArrayList<ILexeme>();

	private static List<ILexeme> parse(Words words) {
		List<ILexeme> result = new ArrayList<ILexeme>();

		if (words.isEmpty()) {
			return result;
		}

		List<ru.zainutdinov.vbs2js.word.Unknown> unknown = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		IWord word = words.cutFirst();

		while (true) {
			Class<?> wordClass = word.getClass();

			if (wordClass.equals(ru.zainutdinov.vbs2js.word.Unknown.class)) {
				unknown.add((ru.zainutdinov.vbs2js.word.Unknown) word);
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Public.class)) {
				result.add(new Public());
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Private.class)) {
				result.add(new Private());
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Return.class)) {
				// TODO
				List<ru.zainutdinov.vbs2js.word.Unknown> return_ = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				return_.add(new ru.zainutdinov.vbs2js.word.Unknown("return "));
				result.add(new Unknown(return_));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.True.class)) {
				// TODO: test
				if (!unknown.isEmpty()) {
					result.add(new Unknown(new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>(unknown)));
					unknown.clear();
				}

				// TODO
				List<ru.zainutdinov.vbs2js.word.Unknown> true_ = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				true_.add(new ru.zainutdinov.vbs2js.word.Unknown("true"));
				result.add(new Unknown(true_));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.False.class)) {
				// TODO: test
				if (!unknown.isEmpty()) {
					result.add(new Unknown(new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>(unknown)));
					unknown.clear();
				}

				// TODO
				List<ru.zainutdinov.vbs2js.word.Unknown> false_ = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				false_.add(new ru.zainutdinov.vbs2js.word.Unknown("false"));
				result.add(new Unknown(false_));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Sub.class)) {
				result.add(new Sub(words));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Function.class)) {
				result.add(new Function(words));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.If.class)) {
				result.add(new If(words));
			} else {
				// TODO: test for new IWord
			}

			if (words.isEmpty()) {
				if (!unknown.isEmpty()) {
					result.add(new Unknown(unknown));
				}

				break;
			}

			word = words.cutFirst();
		}

		return result;
	}

	public Lexemes(Words words) {
		lexemes = parse(words);
	}

	public String js(int tabLevel) {
		String result = "";

		for (ILexeme lexeme : lexemes) {
			result += lexeme.js(tabLevel);
		}

		return result;
	}
}