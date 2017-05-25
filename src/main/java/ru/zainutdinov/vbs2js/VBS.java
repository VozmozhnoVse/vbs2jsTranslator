package ru.zainutdinov.vbs2js;

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

	private String extractExpression(Words words) {
		String result = new String();

		String word = words.cutFirst();
		while (!word.equals("Then")) {
			result += word;
			word = words.cutFirst();
		}

		return result;
	}
	
	private String extractBodyThen(Words words) {
		String result = new String();

		String word = words.cutFirst();
		while (!word.equals("End") && words.nextIs("If")) {
			result += word;
			word = words.cutFirst();
		}

		words.cutFirst();

		return result;
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
				String expression = extractExpression(words);
				String bodyThen = extractBodyThen(words);
				String[] expressions = null;// TODO extractBodyElse(words);
				String[] bodys = null; // TODO: rename
				lexemes.add(new If(expressions, bodys));
			}
			
			word = words.cutFirst();
		}
	}

	public Lexemes lexemes() {
		return lexemes;
	}
}
