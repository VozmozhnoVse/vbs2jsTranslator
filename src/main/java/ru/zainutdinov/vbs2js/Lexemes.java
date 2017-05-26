package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

import ru.zainutdinov.vbs2js.lexeme.ILexeme;

public class Lexemes {

	private List<ILexeme> lexemes = new ArrayList<ILexeme>();

	public void add(ILexeme lexeme) {
		lexemes.add(lexeme);
	}

	public String js(int tabLevel) {
		String result = "";
		
		for (ILexeme lexeme : lexemes) {
			result += lexeme.js(tabLevel);
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Lexemes.class) {
			return false;
		}

		Lexemes lexemes = (Lexemes) obj;

		// TODO: optimize...
		return js(0).equals(lexemes.js(0));
	}
}