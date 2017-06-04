package ru.zainutdinov.vbs2js.lexeme.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.lexeme.Sub;

public class SubTests {

	@Test
	public void testJS_Sub() {
		ru.zainutdinov.vbs2js.word.Unknown name = new ru.zainutdinov.vbs2js.word.Unknown("SubName");
		Words parameters = new Words("");
		parameters.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		List<ILexeme> body = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> bodyWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		bodyWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		body.add(new ru.zainutdinov.vbs2js.lexeme.Unknown(bodyWords));
		Sub sub = new Sub(name, parameters, body);

		Assert.assertEquals("function SubName(Parameters) {\n\tSomething\n}\n", sub.js(0));
		Assert.assertEquals("\tfunction SubName(Parameters) {\n\t\tSomething\n\t}\n", sub.js(1));
	}
}
