package ru.zainutdinov.vbs2js;

public class Sub implements ILexeme {
	String name;
	String parameters;
	
	public Sub(String name, String parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	@Override
	public boolean equals(Object obj) {
		Sub lexeme = (Sub)obj;
		
		// TODO: test
		if (lexeme == null) {
			return false;
		}

		// TODO: test
		if (this.name.equals(lexeme.name) && this.parameters.equals(lexeme.parameters)) {
			return true;
		}

		return false;
	}

	@Override
	public String js() {
		return new String("function " + name + "(" + parameters + ") {\n}\n");
	}
}
