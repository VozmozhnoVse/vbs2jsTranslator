package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.Function;

public class FunctionTests {

	@Test
	public void testJS_Function() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());
		Function function = new Function(words);

		Assert.assertEquals("function FunctionName(Parameters) {\n\tSomething\n}\n", function.js(0));
		Assert.assertEquals("\tfunction FunctionName(Parameters) {\n\t\tSomething\n\t}\n", function.js(1));
	}
}
