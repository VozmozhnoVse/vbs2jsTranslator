package ru.zainutdinov.vbs2js;

public class Public implements ILexeme {

	@Override
	public String js() {
		return new String();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Public.class) {
			return true;
		}

		return false;
	}
}
