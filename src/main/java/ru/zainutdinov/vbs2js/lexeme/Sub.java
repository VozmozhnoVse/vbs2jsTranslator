package ru.zainutdinov.vbs2js.lexeme;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.StringUtils;
import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.word.EndSub;
import ru.zainutdinov.vbs2js.word.ParenthesisClose;
import ru.zainutdinov.vbs2js.word.ParenthesisOpen;

public class Sub implements ILexeme {
	private ru.zainutdinov.vbs2js.word.Unknown name;
	private Words parameters;
	private Lexemes body;

	private static Words extractParameters(Words words) {
		if (!words.checkFirstClass(ParenthesisOpen.class)) {
			return new Words("");
		}

		return words.cutToInclusive(ParenthesisClose.class);
	}

	public Sub(Words words) {
		this.name = (ru.zainutdinov.vbs2js.word.Unknown) words.cutFirst();
		this.parameters = extractParameters(words);
		this.body = new Lexemes(words.cutToExclusive(EndSub.class));
	}

	@Override
	public String js(int tabLevel) {
		String tabs = StringUtils.repeat("\t", tabLevel);
		String result = "";

		result += tabs + "function " + name.getText() + "(";

		result += parameters.js();

		result += ") {\n";

		result += body.js(tabLevel + 1);

		result += tabs + "}\n";

		return result;
	}
}
