package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class LexemeTests {

	@Test
	public void lexemeTests_SampleSub() {
		String vbsCode = "Sub Main\nEnd Sub";

		ArrayList<String> lexemes = new Vbs2JsTranslator().lexemes(vbsCode);

		Assert.assertEquals(7, lexemes.size());
		Assert.assertEquals("Sub", lexemes.get(0));
		Assert.assertEquals(" ", lexemes.get(1));
		Assert.assertEquals("Main", lexemes.get(2));
		Assert.assertEquals("\n", lexemes.get(3));
		Assert.assertEquals("End", lexemes.get(4));
		Assert.assertEquals(" ", lexemes.get(5));
		Assert.assertEquals("Sub", lexemes.get(6));
	}
}
