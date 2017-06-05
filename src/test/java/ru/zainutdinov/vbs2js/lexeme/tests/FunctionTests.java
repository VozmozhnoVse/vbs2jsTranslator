package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.Function;

public class FunctionTests {

	@Test
	public void testJS_Function() {
		ru.zainutdinov.vbs2js.word.Unknown name = new ru.zainutdinov.vbs2js.word.Unknown("FunctionName");
		Words parameters = new Words("");
		parameters.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		Words bodyWords = new Words("");
		bodyWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		Lexemes body = new Lexemes(bodyWords);
		Function function = new Function(name, parameters, body);

		Assert.assertEquals("function FunctionName(Parameters) {\n\tSomething\n}\n", function.js(0));
		Assert.assertEquals("\tfunction FunctionName(Parameters) {\n\t\tSomething\n\t}\n", function.js(1));
	}
}
