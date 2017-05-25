package ru.zainutdinov.vbs2js;

public class Private implements ILexeme {

	@Override
	public String js() {
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
