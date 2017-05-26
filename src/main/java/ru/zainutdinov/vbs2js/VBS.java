package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

public class VBS {

	private Lexemes lexemes = new Lexemes();
	
	private String extractParameters(Words words) {
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

	private String extractBody(Words words, String endString) {
		String body = new String();
		
		String word = words.cutFirst();
		while (!(word.equals("End") && words.nextIs(endString))) {
			body += word;
			word = words.cutFirst();
		}

		words.cutFirst();
		
		return body;
	}

	private String replaceReturn(String name, String code) {
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

	private void extractIf(Words words, List<String> expression, List<String> body) {
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

	public VBS(String code) {
		Words words = new Words(code);

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
				lexemes.add(new Sub(name, parameters, body));			
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
	}

	public Lexemes lexemes() {
		return lexemes;
	}
}
