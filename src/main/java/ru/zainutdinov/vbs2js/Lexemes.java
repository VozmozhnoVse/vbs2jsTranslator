package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.lexeme.If;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;
import ru.zainutdinov.vbs2js.lexeme.Unknown;
import ru.zainutdinov.vbs2js.word.IWord;

public class Lexemes {
	@FunctionalInterface
	private static interface LexemeFactory {
		public ILexeme create(Words words);
	}
	
	private static final Map<Class<? extends IWord>, LexemeFactory> factoryMap = new HashMap<>();
	
	static {
		factoryMap.put(ru.zainutdinov.vbs2js.word.Public.class, Public::new);
		factoryMap.put(ru.zainutdinov.vbs2js.word.Private.class, Private::new);
		factoryMap.put(ru.zainutdinov.vbs2js.word.Sub.class, Sub::new);
		factoryMap.put(ru.zainutdinov.vbs2js.word.Function.class, Function::new);
		factoryMap.put(ru.zainutdinov.vbs2js.word.If.class, If::new);
	}

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
			} else if (factoryMap.containsKey(wordClass)) {
				result.add(factoryMap.get(wordClass).create(words));
				/*
				 * TODO } else if
				 * (wordClass.equals(ru.zainutdinov.vbs2js.word.Return.class)) {
				 * // List<ru.zainutdinov.vbs2js.word.Unknown> return_ = new
				 * ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				 * return_.add(new
				 * ru.zainutdinov.vbs2js.word.Unknown("return "));
				 * result.add(new Unknown(return_)); } else if
				 * (wordClass.equals(ru.zainutdinov.vbs2js.word.True.class)) {
				 * // : test if (!unknown.isEmpty()) { result.add(new
				 * Unknown(new
				 * ArrayList<ru.zainutdinov.vbs2js.word.Unknown>(unknown)));
				 * unknown.clear(); }
				 * 
				 * // List<ru.zainutdinov.vbs2js.word.Unknown> true_ = new
				 * ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				 * true_.add(new ru.zainutdinov.vbs2js.word.Unknown("true"));
				 * result.add(new Unknown(true_)); } else if
				 * (wordClass.equals(ru.zainutdinov.vbs2js.word.False.class)) {
				 * /* // : test if (!unknown.isEmpty()) { result.add(new
				 * Unknown(new
				 * ArrayList<ru.zainutdinov.vbs2js.word.Unknown>(unknown)));
				 * unknown.clear(); }
				 * 
				 * // List<ru.zainutdinov.vbs2js.word.Unknown> false_ = new
				 * ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
				 * false_.add(new ru.zainutdinov.vbs2js.word.Unknown("false"));
				 * result.add(new Unknown(false_));
				 */
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