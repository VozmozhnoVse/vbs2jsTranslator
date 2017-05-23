package ru.zainutdinov.vbs2js;

public class Public implements ILexeme {

	@Override
	public boolean equals(Object obj) {
		Public lexeme = (Public)obj;
		
		// TODO: test
		if (lexeme == null) {
			return false;
		}

		return true;
	}

	@Override
	public String js() {
		return new String();
	}

}
