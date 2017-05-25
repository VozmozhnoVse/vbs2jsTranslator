package ru.zainutdinov.vbs2js;

public class If implements ILexeme {
	String[] expression;
	String[] body;
	
	public If(String[] expression, String[] body) {
		this.expression = expression;
		this.body = body;
	}

	@Override
	public String js() {
		String result = new String();
		
		result += "if " + expression + " {\n";
/*		
		if (!bodyThen.isEmpty()) {
			result += "\t" + bodyThen + "\n";
		}

		if (bodyElse.length > 0) {
			result += "} else {\n\t" + bodyElse + "\n";			
		}
*/
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
	}
}
