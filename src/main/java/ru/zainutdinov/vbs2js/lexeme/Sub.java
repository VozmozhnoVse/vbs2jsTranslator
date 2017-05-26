package ru.zainutdinov.vbs2js.lexeme;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.StringUtils;

public class Sub implements ILexeme {
	private String name;
	private String parameters;
	private Lexemes body;

	public Sub(String name, String parameters, Lexemes body) {
		this.name = name;
		this.parameters = parameters;
		
		if (body == null) {
			this.body = new Lexemes();
		}
		else {
			this.body = body;
		}
	}

	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = "";
		
		result += tabs + "function " + name + "(" + parameters + ") {\n";

		result += body.js(tabLevel + 1);

		result += tabs + "}\n";
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Sub.class) {
			return false;
		}

		Sub sub = (Sub) obj;
		
		return name.equals(sub.name) && parameters.equals(sub.parameters) && body.equals(sub.body);
	}
}
