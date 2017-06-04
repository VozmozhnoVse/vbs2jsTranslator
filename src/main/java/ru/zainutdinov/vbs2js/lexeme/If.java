package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

import ru.zainutdinov.vbs2js.StringUtils;
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

			List<ILexeme> currentBody = body.get(j);

			for (int i = 0; i < currentBody.size(); i++) {
				result += currentBody.get(i).js(tabLevel + 1);
			}

			result += tabs + "}\n";
		}

		if (body.size() > j) {
			result += tabs + "else {\n";

			List<ILexeme> currentBody = body.get(j);

			for (int i = 0; i < currentBody.size(); i++) {
				result += currentBody.get(i).js(tabLevel + 1);
			}

			result += tabs + "}\n";
		}

		return result;
	}
}
