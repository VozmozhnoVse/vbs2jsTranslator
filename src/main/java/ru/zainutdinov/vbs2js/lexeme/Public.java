package ru.zainutdinov.vbs2js.lexeme;

public class Public implements ILexeme {

	@Override
	public String js() {
		return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Public.class) {
			return true;
		}

		return false;
	}
}
