package ru.zainutdinov.vbs2js.lexeme;

import ru.zainutdinov.vbs2js.StringUtils;

public class Unknown implements ILexeme {

	private String value = new String();
	
	public Unknown(String value) {
		this.value = value;
		
		if (!value.isEmpty()) {
			this.value += "\n";
		}
	}
	
	@Override
	public String js(int tabLevel) {
		if (value.isEmpty()) {
			return value;
		}

		return StringUtils.repeat("\t", tabLevel) + value;
	}

}
