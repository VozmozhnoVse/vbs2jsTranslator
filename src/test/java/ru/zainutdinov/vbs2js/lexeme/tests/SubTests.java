package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.Sub;

public class SubTests {

	@Test
	public void testJS_Sub() {
		ru.zainutdinov.vbs2js.word.Unknown name = new ru.zainutdinov.vbs2js.word.Unknown("SubName");
		Words parameters = new Words("");
		parameters.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		Words bodyWords = new Words("");
		bodyWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		Lexemes body = new Lexemes(bodyWords);
		Sub sub = new Sub(name, parameters, body);

		Assert.assertEquals("function SubName(Parameters) {\n\tSomething\n}\n", sub.js(0));
		Assert.assertEquals("\tfunction SubName(Parameters) {\n\t\tSomething\n\t}\n", sub.js(1));
	}
}
