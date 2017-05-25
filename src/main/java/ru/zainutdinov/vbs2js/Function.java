package ru.zainutdinov.vbs2js;

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
	public String js() {
		String result = "";
		
		result += "function " + name + "(" + parameters + ") {\n";
		
		if (!body.isEmpty()) {
			result +=  "\t" + body + "\n";
		}

		result += "}\n";
		
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
