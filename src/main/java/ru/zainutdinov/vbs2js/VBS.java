package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.If;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;
import ru.zainutdinov.vbs2js.lexeme.Unknown;

public class VBS {

	private Lexemes lexemes = new Lexemes();
	
	private static String extractParameters(Words words) {
		String parameters = "";
		
		if (words.nextIs("(")) {
			words.cutFirst();
			
			String word = words.cutFirst();
			while (!word.equals(")")) {
				parameters += word;
				word = words.cutFirst();
			}
		}

		return parameters;
	}

	private static String extractBody(Words words, String endString) {
		String body = new String();
		
		String word = words.cutFirst();
		while (!(word.equals("End") && words.nextIs(endString))) {
			body += word;
			word = words.cutFirst();
		}

		words.cutFirst();
		
		return body;
	}

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
		
		while (!(word.equals("End") && words.nextIs("If"))) {
			
			if (scope.equals("expression")) {
				if (word.equals("Then")) {
					scope = "body";
					expression.add(nextExpression);
					nextExpression = "";
				}
				else {
					nextExpression += word;
				}
			} else if (scope.equals("body")) {
				if (word.equals("ElseIf")) {
					scope = "expression";
					body.add(nextBody);
					nextBody = "";
				}
				else if (word.equals("Else")) {
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

	private static Lexemes parse(Words words) {
		Lexemes lexemes = new Lexemes();
		
		String word = words.cutFirst();

		while (word != null) {
			if ("Public".equals(word)) {
				lexemes.add(new Public());
			}
			if ("Private".equals(word)) {
				lexemes.add(new Private());
			} else if ("Sub".equals(word)) {
				String name = words.cutFirst();
				String parameters = extractParameters(words);
				String body = extractBody(words, "Sub");
				// TODO optimize conversion words -> string -> words
				// TODO call new parse recursively
				Lexemes lexemes1 = new Lexemes();
				lexemes1.add(new Unknown(body));
				lexemes.add(new Sub(name, parameters, lexemes1));			
			} else if ("Function".equals(word)) {
				String name = words.cutFirst();
				String parameters = extractParameters(words);
				String body = extractBody(words, "Function");
				body = replaceReturn(name, body);
				lexemes.add(new Function(name, parameters, body));			
			} else if ("If".equals(word)) {
				List<String> expression = new ArrayList<String>();
				List<String> body = new ArrayList<String>();
				extractIf(words, expression, body);
				lexemes.add(new If(expression, body));
			}
			
			word = words.cutFirst();
		}

		return lexemes;
	}
	
	public VBS(String code) {
		Words words = new Words(code);

		lexemes = parse(words);
	}

	public Lexemes lexemes() {
		return lexemes;
	}
}
