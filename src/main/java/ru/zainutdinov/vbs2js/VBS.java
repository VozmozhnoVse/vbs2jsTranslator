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

public class VBS {
	private static String replaceReturn(String name, String code) {
		Words words = new Words(code);
		String result = new String();
		
		String word = words.cutFirst();
		while (word != null) {
			if (word.equals(name) && words.nextIs(" = ")) {
				words.cutFirst();
				result += "return " + words.cutFirst() + ";";
			}
			else {
				result += word;
			}
			word = words.cutFirst();
		}
		
		return result;
	}

	private static void extractIf(Words words, List<String> expression, List<String> body) {
		String word = words.cutFirst();

		String nextExpression = "";
		String nextBody = "";
		String scope = "expression";
		
		while (!(word.equals("end") && words.nextIs("if"))) {
			
			if (scope.equals("expression")) {
				if (word.equals("then")) {
					scope = "body";
					expression.add(nextExpression);
					nextExpression = "";
				}
				else {
					nextExpression += word;
				}
			} else if (scope.equals("body")) {
				if (word.equals("elseif")) {
					scope = "expression";
					body.add(nextBody);
					nextBody = "";
				}
				else if (word.equals("else")) {
					body.add(nextBody);
					nextBody = "";
				}
				else {
					nextBody += word;
				}
			}
			
			word = words.cutFirst();
		}

		body.add(nextBody);
		words.cutFirst();
	}

	public static List<ILexeme> parse(Words words) {
		String unknown = "";
		List<ILexeme> lexemes = new ArrayList<ILexeme>();

		IWord word = words.cutFirst();

		while (word != null) {
			if (word.equals(new ru.zainutdinov.vbs2js.word.Public())) {
				if (!unknown.isEmpty()) {
					lexemes.add(new Unknown(unknown));
					unknown = "";
				}

				lexemes.add(new Public());
			}
			else if (word.equals(new ru.zainutdinov.vbs2js.word.Private())) {
				if (!unknown.isEmpty()) {
					lexemes.add(new Unknown(unknown));
					unknown = "";
				}

				lexemes.add(new Private());
			}
			else if (word.equals(new ru.zainutdinov.vbs2js.word.Sub())) {
				if (!unknown.isEmpty()) {
					lexemes.add(new Unknown(unknown));
					unknown = "";
				}

				IWord name = words.cutFirst();
				String parameters = extractParameters(words);
				// TODO: optimize conversion words -> string -> words
				Words body = new Words(extractBody(words, "sub"));
				lexemes.add(new Sub(name.js(), parameters, parse(body)));			
			}
			else if (word.equals(new ru.zainutdinov.vbs2js.word.Function())) {
				if (!unknown.isEmpty()) {
					lexemes.add(new Unknown(unknown));
					unknown = "";
				}

				IWord name = words.cutFirst();
				String parameters = extractParameters(words);
				String body = extractBody(words, "function");
				body = replaceReturn(name.js(), body);
				// 
				lexemes.add(new Function(name.js(), parameters, parse(new Words(body))));
			}
			else if (word.equals(new ru.zainutdinov.vbs2js.word.If())) {
				if (!unknown.isEmpty()) {
					lexemes.add(new Unknown(unknown));
					unknown = "";
				}

				List<String> expression = new ArrayList<String>();
				List<String> body = new ArrayList<String>();
				extractIf(words, expression, body);
				lexemes.add(new If(expression, body));
			}
			else {
				unknown += word;
			}
			
			word = words.cutFirst();
		}

		if (!unknown.isEmpty()) {
			lexemes.add(new Unknown(unknown));
		}

		return lexemes;
	}
}
