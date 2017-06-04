package ru.zainutdinov.vbs2js.lexeme.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;

public class FunctionTests {

	@Test
	public void testJS_Function() {
		ru.zainutdinov.vbs2js.word.Unknown name = new ru.zainutdinov.vbs2js.word.Unknown("FunctionName");
		Words parameters = new Words("");
		parameters.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		List<ILexeme> body = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> bodyWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		bodyWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		body.add(new ru.zainutdinov.vbs2js.lexeme.Unknown(bodyWords));
		Function function = new Function(name, parameters, body);

		Assert.assertEquals("function FunctionName(Parameters) {\n\tSomething\n}\n", function.js(0));
		Assert.assertEquals("\tfunction FunctionName(Parameters) {\n\t\tSomething\n\t}\n", function.js(1));
	}
}
