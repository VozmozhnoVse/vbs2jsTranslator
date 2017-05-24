package ru.zainutdinov.vbs2js;

public class Sub implements ILexeme {
	String name;
	String parameters;
	String body;
	
	public Sub(String name, String parameters, String body) {
		this.name = name;
		this.parameters = parameters;
		this.body = body;
	}

	@Override
	public String js() {
		String result = new String();
		
		result += "function " + name + "(" + parameters + ") {\n";
		
		if (!body.isEmpty()) {
			result += body + "\n";
		}

		result += "}\n";
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Sub.class) {
			return false;
		}

		Sub sub = (Sub)obj;
		
		return name.equals(sub.name) && parameters.equals(sub.parameters) && body.equals(sub.body);
	}
}
