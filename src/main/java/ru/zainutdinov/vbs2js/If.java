package ru.zainutdinov.vbs2js;

public class If implements ILexeme {
	String expression;
	String bodyThen;
	
	public If(String expression, String bodyThen) {
		this.expression = expression;
		this.bodyThen = bodyThen;
	}

	@Override
	public String js() {
		String result = new String();
		
		result += "if " + expression + " {\n";
		
		if (!bodyThen.isEmpty()) {
			result += "\t" + bodyThen + "\n";
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
		
		return expression.equals(if_.expression) && bodyThen.equals(if_.bodyThen);
	}
}
