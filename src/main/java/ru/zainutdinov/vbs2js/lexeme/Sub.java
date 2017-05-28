package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

import ru.zainutdinov.vbs2js.StringUtils;
import ru.zainutdinov.vbs2js.word.IWord;

public class Sub implements ILexeme {
	private ru.zainutdinov.vbs2js.word.Unknown name;
	private List<IWord> parameters;
	private List<ILexeme> body;

	public Sub(ru.zainutdinov.vbs2js.word.Unknown name, List<IWord> parameters, List<ILexeme> body) {
		this.name = name;
		this.parameters = parameters;
		this.body = body;
	}

	// TODO: tests
	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = "";
		
		result += tabs + "function " + name.getText() + "(" + /*parameters + */") {\n";
		/* TODO

		result += body.js(tabLevel + 1);
*/
		result += tabs + "}\n";		

		return result;
	}

	public ru.zainutdinov.vbs2js.word.Unknown getName() {
		return name;
	}

	public List<IWord> getParameters() {
		return parameters;
	}

	public List<ILexeme> getBody() {
		return body;
	}
	/* TODO
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Sub.class) {
			return false;
		}

		Sub sub = (Sub) obj;
		
		return name.equals(sub.name) && parameters.equals(sub.parameters) && body.equals(sub.body);
	} */
}
