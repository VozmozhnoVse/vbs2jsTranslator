package ru.zainutdinov.vbs2js.lexeme;

import ru.zainutdinov.vbs2js.StringUtils;

public class Function implements ILexeme {
	private String name;
	private String parameters;
	private String body;
	
	public Function(String name, String parameters, String body) {
		this.name = name;
		this.parameters = parameters;
		this.body = body;
	}

	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = "";
		
		result += tabs + "function " + name + "(" + parameters + ") {\n";
		
		if (!body.isEmpty()) {
			// TODO body.js(tabLevel + 1)
			result +=  tabs + "\t" + body + "\n";
		}

		result += tabs + "}\n";
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Function.class) {
			return false;
		}

		Function function = (Function) obj;
		
		return name.equals(function.name) && parameters.equals(function.parameters) && body.equals(function.body);
	}
}
