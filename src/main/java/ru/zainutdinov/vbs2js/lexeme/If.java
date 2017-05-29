package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

import ru.zainutdinov.vbs2js.word.IWord;

public class If implements ILexeme {
	List<List<IWord>> expression;
	List<List<ILexeme>> body;
	
	public If(List<List<IWord>> expression, List<List<ILexeme>> body) {
		this.expression = expression;
		this.body = body;
	}

	public List<IWord> getExpression(int i) {
		return expression.get(i);
	}

	public List<ILexeme> getBody(int i) {
		return body.get(i);
	}
	
	@Override
	public String js(int tabLevel) {
//		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = new String();
/* TODO
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
		*/
		return result;
	}

	/* TODO @Override
	public boolean equals(Object obj) {
		if (obj.getClass() != If.class) {
			return false;
		}

		If if_ = (If)obj;

		return expression.equals(if_.expression) && body.equals(if_.body);
		//return js().equals(if_.js());
	}*/
}
