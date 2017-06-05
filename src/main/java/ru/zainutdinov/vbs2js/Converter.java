package ru.zainutdinov.vbs2js;

public class Converter {

	private String vbs;

	public Converter(String vbs) {
		this.vbs = vbs;
	}

	public String js() {
		Words words = new Words(vbs);
		Lexemes lexemes = new Lexemes(words);

		return lexemes.js(0);
	}
}
