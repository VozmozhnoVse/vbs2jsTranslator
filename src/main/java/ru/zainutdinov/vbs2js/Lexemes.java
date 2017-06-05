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
import ru.zainutdinov.vbs2js.word.Else;
import ru.zainutdinov.vbs2js.word.ElseIf;
import ru.zainutdinov.vbs2js.word.EndFunction;
import ru.zainutdinov.vbs2js.word.EndIf;
import ru.zainutdinov.vbs2js.word.EndSub;
import ru.zainutdinov.vbs2js.word.IWord;
import ru.zainutdinov.vbs2js.word.ParenthesisClose;
import ru.zainutdinov.vbs2js.word.ParenthesisOpen;
import ru.zainutdinov.vbs2js.word.Then;

public class Lexemes {

	private List<ILexeme> lexemes = new ArrayList<ILexeme>();

	private static Words extractParameters(Words words) {
		if (!words.checkFirstClass(ParenthesisOpen.class)) {
			return new Words("");
		}

		return words.cutToInclusive(ParenthesisClose.class);
	}

	private static Words extractBodySub(Words words) {
		return words.cutToExclusive(EndSub.class);
	}

	private static Words extractBodyFunction(Words words) {
		return words.cutToExclusive(EndFunction.class);
	}

	private static void extractIf(Words words, List<List<IWord>> expression, List<List<ILexeme>> body) {
		String scope = "expression";
		List<IWord> nextExpression = new ArrayList<IWord>();
		Words nextBody = new Words("");

		IWord word = words.cutFirst();
		while (!word.getClass().equals(EndIf.class)) {
			if (scope.equals("expression")) {
				if (word.getClass().equals(Then.class)) {
					scope = "body";
					expression.add(new ArrayList<IWord>(nextExpression));
					nextExpression.clear();
					word = words.cutFirst();
				} else {
					nextExpression.add(word);
					word = words.cutFirst();
				}
			} else if (scope.equals("body")) {
				if (word.getClass().equals(Else.class)) {
					body.add(parse(nextBody));
					nextBody.clear();
					word = words.cutFirst();
				} else if (word.getClass().equals(ElseIf.class)) {
					scope = "expression";
					body.add(parse(nextBody));
					nextBody.clear();
					word = words.cutFirst();
				} else {
					nextBody.add(word);
					word = words.cutFirst();
				}
			}
		}

		body.add(parse(nextBody));
	}

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
				ru.zainutdinov.vbs2js.word.Unknown name = (ru.zainutdinov.vbs2js.word.Unknown) words.cutFirst();

				Words parameters = extractParameters(words);
				Lexemes body = new Lexemes(extractBodySub(words));
				result.add(new Sub(name, parameters, body));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.Function.class)) {
				ru.zainutdinov.vbs2js.word.Unknown name = (ru.zainutdinov.vbs2js.word.Unknown) words.cutFirst();

				Words parameters = extractParameters(words);
				List<ILexeme> body = parse(extractBodyFunction(words));
				result.add(new Function(name, parameters, body));
			} else if (wordClass.equals(ru.zainutdinov.vbs2js.word.If.class)) {
				List<List<IWord>> expression = new ArrayList<List<IWord>>();
				List<List<ILexeme>> body = new ArrayList<List<ILexeme>>();
				extractIf(words, expression, body);
				result.add(new If(expression, body));
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