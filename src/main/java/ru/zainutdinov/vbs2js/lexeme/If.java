package ru.zainutdinov.vbs2js.lexeme;

import java.util.ArrayList;
import java.util.List;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.StringUtils;
import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.word.Else;
import ru.zainutdinov.vbs2js.word.ElseIf;
import ru.zainutdinov.vbs2js.word.EndIf;
import ru.zainutdinov.vbs2js.word.IWord;
import ru.zainutdinov.vbs2js.word.Then;

public class If implements ILexeme {
	List<List<IWord>> expression;
	List<Lexemes> body;

	private static void extractIf(Words words, List<List<IWord>> expression, List<Lexemes> body) {
		String scope = "expression";
		List<IWord> nextExpression = new ArrayList<IWord>();
		Words nextBody = new Words("");

		IWord word = words.cutFirst();
		while (!word.getClass().equals(EndIf.class)) {
			if (scope.equals("expression")) {
				if (word.getClass().equals(Then.class)) {
					scope = "body";
					expression.add(new ArrayList<IWord>(nextExpression));
					nextExpression.clear();
					word = words.cutFirst();
				} else {
					nextExpression.add(word);
					word = words.cutFirst();
				}
			} else if (scope.equals("body")) {
				if (word.getClass().equals(Else.class)) {
					body.add(new Lexemes(nextBody));
					nextBody.clear();
					word = words.cutFirst();
				} else if (word.getClass().equals(ElseIf.class)) {
					scope = "expression";
					body.add(new Lexemes(nextBody));
					nextBody.clear();
					word = words.cutFirst();
				} else {
					nextBody.add(word);
					word = words.cutFirst();
				}
			}
		}

		body.add(new Lexemes(nextBody));
	}

	public If(Words words) {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();
		List<Lexemes> body = new ArrayList<Lexemes>();
		extractIf(words, expression, body);

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
