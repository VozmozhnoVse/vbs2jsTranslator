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

	private String extractBody(Words words) {
		String body = new String();
		
		String word = words.cutFirst();
		while (!(word.equals("End") && words.nextIs("Sub"))) {
			body += words;
			word = words.cutFirst();
		}

		words.cutFirst();
		
		return body;
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
			}
			else if ("Sub".equals(word)) {
				String name = words.cutFirst();
				String parameters = extractParameters(words);
				String body = extractBody(words);
				lexemes.add(new Sub(name, parameters, body));			
			}
			
			word = words.cutFirst();
		}
	}

	public Lexemes lexemes() {
		return lexemes;
	}
}
