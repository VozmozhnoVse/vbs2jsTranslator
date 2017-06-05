package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.Sub;

public class SubTests {

	@Test
	public void testJS_Sub() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Something"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());
		Sub sub = new Sub(words);

		Assert.assertEquals("function SubName(Parameters) {\n\tSomething\n}\n", sub.js(0));
		Assert.assertEquals("\tfunction SubName(Parameters) {\n\t\tSomething\n\t}\n", sub.js(1));
	}
}
