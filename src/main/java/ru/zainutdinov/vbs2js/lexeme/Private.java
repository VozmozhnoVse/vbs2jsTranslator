package ru.zainutdinov.vbs2js.lexeme;

public class Private implements ILexeme {

	@Override
	public String js(int tabLevel) {
		return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Private.class) {
			return true;
		}

		return false;
	}
}
