package ru.zainutdinov.vbs2js.lexeme;

public class Sub implements ILexeme {
	private String name;
	private String parameters;
	private String body;
	
	public Sub(String name, String parameters, String body) {
		this.name = name;
		this.parameters = parameters;
		this.body = body;
	}

	@Override
	public String js() {
		String result = "";
		
		result += "function " + name + "(" + parameters + ") {\n";
		
		if (!body.isEmpty()) {
			result += "\t" + body + "\n";
		}

		result += "}\n";
		
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
