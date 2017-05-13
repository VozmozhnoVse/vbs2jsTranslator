package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class Vbs2JsTranslatorTest {

	@Test
	public void simpleSubTest() {
		String vbsCode = "Sub Main\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translate(vbsCode);

		Assert.assertEquals("function Main() {\n}", jsCode);
	}

	@Test
	public void simplePublicSubTest() {
		String vbsCode = "Public Sub Main\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translate(vbsCode);

		Assert.assertEquals("function Main() {\n}", jsCode);
	}

	@Test
	public void simplePrivateSubTest() {
		String vbsCode = "Private Sub Main\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translate(vbsCode);

		Assert.assertEquals("function Main() {\n}", jsCode);
	}
}
