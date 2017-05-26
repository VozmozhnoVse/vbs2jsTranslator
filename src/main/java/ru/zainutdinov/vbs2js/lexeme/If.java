package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

public class If implements ILexeme {
	List<String> expression;
	List<String> body;
	
	public If(List<String> expression, List<String> body) {
		this.expression = expression;
		this.body = body;
	}

	@Override
	public String js() {
		String result = new String();

		int i = 0;
		for (; i < expression.size(); i++) {
		
			if (i > 0) {
				result += "} else ";
			}
			
			result += "if " + expression.get(i) + " {\n";
			
			if (!body.get(i).isEmpty()) {
				result += "\t" + body.get(i) + "\n";
			}
		}

		if (body.size() > i) {
			if (!body.get(i).isEmpty()) {
				result += "} else {\n\t" + body.get(i) + "\n";
			}
		}

		result += "}\n";
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != If.class) {
			return false;
		}

		If if_ = (If)obj;

		return expression.equals(if_.expression) && body.equals(if_.body);
		//return js().equals(if_.js());
	}
}
