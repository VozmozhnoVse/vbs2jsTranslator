package ru.zainutdinov.vbs2js;

import java.util.ArrayList;
import java.util.List;

public class Lexemes {

	List<ILexeme> lexemes = new ArrayList<ILexeme>();
	
	public int size() {
		return lexemes.size();
	}

	public ILexeme get(int i) {
		return lexemes.get(i);
	}

	public void add(ILexeme lexeme) {
		lexemes.add(lexeme);
	}

	public String js() {
		String result = new String();
		
		for (int i = 0; i < lexemes.size(); i++) {
			result += lexemes.get(i).js();
		}

		return result;
	}
}