package ru.zainutdinov.vbs2js;

import ru.zainutdinov.vbs2js.lexeme.ILexeme;

public class Converter {

	private String vbs;
	
	public Converter(String vbs) {
		this.vbs = vbs;
	}

	public String js() {
		Words words = new Words(vbs);
		Lexemes lexemes = new Lexemes(words.getWords());

		String result = "";
		
		for (ILexeme lexeme : lexemes.getLexemes()) {
			result += lexeme.js(0);
		}

		return result;
	}
}
