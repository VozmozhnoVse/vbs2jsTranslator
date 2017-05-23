package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.ILexeme;
import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.Sub;
import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class LexemeTests {

	@Test
	public void wordsTests_SimpleSub() {
		String vbsCode = "Sub Main\nEnd Sub";

		ArrayList<String> words = new Vbs2JsTranslator().words(vbsCode);

		Assert.assertEquals(7, words.size());
		Assert.assertEquals("Sub", words.get(0));
		Assert.assertEquals(" ", words.get(1));
		Assert.assertEquals("Main", words.get(2));
		Assert.assertEquals("\n", words.get(3));
		Assert.assertEquals("End", words.get(4));
		Assert.assertEquals(" ", words.get(5));
		Assert.assertEquals("Sub", words.get(6));
	}

	@Test
	public void lexemesTests_SimpleSub() {
		String vbsCode = "Sub Main\nEnd Sub";

		ArrayList<ILexeme> lexemes = new Vbs2JsTranslator().lexemes(vbsCode);

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Sub("Main", ""), lexemes.get(0));
	}

	@Test
	public void lexemesTests_SimpleSubWithParameters() {
		String vbsCode = "Sub Main(Parameter)\nEnd Sub";

		ArrayList<ILexeme> lexemes = new Vbs2JsTranslator().lexemes(vbsCode);

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Sub("Main", "Parameter"), lexemes.get(0));
	}

	@Test
	public void lexemesTests_Public() {
		String vbsCode = "Public";

		ArrayList<ILexeme> lexemes = new Vbs2JsTranslator().lexemes(vbsCode);

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Public(), lexemes.get(0));
	}

	@Test
	public void jsTests_SimpleSub() {
		Assert.assertEquals("function Main() {\n}", new Sub("Main", "").js());
	}
}