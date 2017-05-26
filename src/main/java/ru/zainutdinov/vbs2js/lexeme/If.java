package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

import ru.zainutdinov.vbs2js.StringUtils;

public class If implements ILexeme {
	List<String> expression;
	List<String> body;
	
	public If(List<String> expression, List<String> body) {
		this.expression = expression;
		this.body = body;
	}

	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = new String();

		int i = 0;
		for (; i < expression.size(); i++) {
		
			result += tabs;

			if (i > 0) {
				result += "} else ";
			}
			
			result += "if " + expression.get(i) + " {\n";
			
			if (!body.get(i).isEmpty()) {
				// TODO body.js(tabLevel + 1)
				result += tabs + "\t" + body.get(i) + "\n";
			}
		}

		if (body.size() > i) {
			if (!body.get(i).isEmpty()) {
				result += tabs + "} else {\n";
				// TODO body.js(tabLevel + 1)
				result += tabs + "\t" + body.get(i) + "\n";
			}
		}

		result += tabs + "}\n";
		
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
