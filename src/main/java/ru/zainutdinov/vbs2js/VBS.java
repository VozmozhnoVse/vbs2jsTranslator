package ru.zainutdinov.vbs2js;

public class VBS {

	private Lexemes lexemes = new Lexemes();
	
	private String extractParameters(Words words) {
		String parameters = new String();
		
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

	/* TODO: delete
	private String replaceReturn(String functionName, String vbsCode) {
		String result = vbsCode;

		int returnPosition = result.indexOf(functionName + " =");

		if (returnPosition >= 0) {
			int endPosition = result.indexOf("\n", returnPosition + functionName.length() + 3);

			result = result.substring(0, endPosition) + ";" + result.substring(endPosition, result.length());
			result = result.substring(0, returnPosition) + "return" + result.substring( + functionName.length() + 2 + returnPosition, result.length());

			result = replaceReturn(functionName, result);
		}

		return result;
	}*/

	public VBS(String code) {
		Words words = new Words(code);

		String word = words.cutFirst();

		while (word != null) {
			if ("Public".equals(word)) {
				lexemes.add(new Public());
			}
			if ("Private".equals(word)) {
				lexemes.add(new Private());
			}
			else if ("Sub".equals(word)) {
				String name = words.cutFirst();
				String parameters = extractParameters(words);
				String body = extractBody(words, "Sub");
				lexemes.add(new Sub(name, parameters, body));			
			}
			else if ("Function".equals(word)) {
				String name = words.cutFirst();
				String parameters = extractParameters(words);
				String body = extractBody(words, "Function");
				// TODO: body = replaceReturn(name, body);
				lexemes.add(new Function(name, parameters, body));			
			}
			
			word = words.cutFirst();
		}
	}

	public Lexemes lexemes() {
		return lexemes;
	}
}
