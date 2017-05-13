package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class Vbs2JsTranslatorTest {
	private static String JS_ETALON = "function Main() {\n}";

	@Test
	public void simpleSubTest() {
		String vbsCode = "Public Sub Main\nEnd Sub";

		Vbs2JsTranslator translator = new Vbs2JsTranslator();

		String jsCode = translator.translate(vbsCode);

		Assert.assertEquals(JS_ETALON, jsCode);
	}
}
