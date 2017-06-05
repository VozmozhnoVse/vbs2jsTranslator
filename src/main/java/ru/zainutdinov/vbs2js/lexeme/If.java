package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.StringUtils;
import ru.zainutdinov.vbs2js.word.IWord;

public class If implements ILexeme {
	List<List<IWord>> expression;
	List<Lexemes> body;

	public If(List<List<IWord>> expression, List<Lexemes> body) {
		this.expression = expression;
		this.body = body;
	}

	public List<IWord> getExpression(int i) {
		return expression.get(i);
	}

	public Lexemes getBody(int i) {
		return body.get(i);
	}

	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = "";

		int j = 0;
		for (; j < expression.size(); j++) {
			if (j > 0) {
				result += tabs + "else if (";
			} else {
				result += tabs + "if (";
			}

			List<IWord> currentExpression = expression.get(j);
			for (int i = 0; i < currentExpression.size(); i++) {
				IWord currentWord = currentExpression.get(i);
				// TODO: ?
				if (currentWord.getClass().equals(ru.zainutdinov.vbs2js.word.Unknown.class)) {
					result += ((ru.zainutdinov.vbs2js.word.Unknown) currentWord).getText();
				}
			}

			result += ") {\n";

			Lexemes currentBody = body.get(j);

			result += currentBody.js(tabLevel + 1);

			result += tabs + "}\n";
		}

		if (body.size() > j) {
			result += tabs + "else {\n";

			Lexemes currentBody = body.get(j);

			result += currentBody.js(tabLevel + 1);

			result += tabs + "}\n";
		}

		return result;
	}
}
