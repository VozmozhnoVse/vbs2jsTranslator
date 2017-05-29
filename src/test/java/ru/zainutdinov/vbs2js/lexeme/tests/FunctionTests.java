package ru.zainutdinov.vbs2js.lexeme.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.word.Comma;
import ru.zainutdinov.vbs2js.word.IWord;

public class FunctionTests {

	@Test
	public void testJS_Function() {
		ru.zainutdinov.vbs2js.word.Unknown name = new ru.zainutdinov.vbs2js.word.Unknown("FunctionName");
		List<IWord> parameters = new ArrayList<IWord>();
		parameters.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter1"));
		parameters.add(new Comma());
		parameters.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter2"));
		List<ILexeme> body = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> bodyWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		bodyWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		body.add(new ru.zainutdinov.vbs2js.lexeme.Unknown(bodyWords));
		Function function = new Function(name, parameters, body);

		Assert.assertEquals("function FunctionName(Parameter1, Parameter2) {\n\tSomething\n}\n", function.js(0));
		Assert.assertEquals("\tfunction FunctionName(Parameter1, Parameter2) {\n\t\tSomething\n\t}\n", function.js(1));
	}
}
