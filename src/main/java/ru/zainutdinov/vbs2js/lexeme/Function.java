package ru.zainutdinov.vbs2js.lexeme;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.StringUtils;
import ru.zainutdinov.vbs2js.Words;

public class Function implements ILexeme {
	private ru.zainutdinov.vbs2js.word.Unknown name;
	private Words parameters;
	private Lexemes body;

	public Function(ru.zainutdinov.vbs2js.word.Unknown name, Words parameters, Lexemes body) {
		this.name = name;
		this.parameters = parameters;
		this.body = body;
	}

	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = "";

		result += tabs + "function " + name.getText() + "(";

		result += parameters.js();

		result += ") {\n";

		result += body.js(tabLevel + 1);

		result += tabs + "}\n";

		return result;
	}
}
