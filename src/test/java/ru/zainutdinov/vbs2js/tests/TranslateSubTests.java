package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateSubTests {

	@Test
	public void translateSubTest_Simple() {
		String vbsCode = "Sub Main\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateSub(vbsCode);

		Assert.assertEquals("function Main() {\n}", jsCode);
	}

	@Test
	public void translateSubTest_Public() {
		String vbsCode = "Public Sub Main\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateSub(vbsCode);

		Assert.assertEquals("function Main() {\n}", jsCode);
	}

	@Test
	public void translateSubTest_Private() {
		String vbsCode = "Private Sub Main\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateSub(vbsCode);

		Assert.assertEquals("function Main() {\n}", jsCode);
	}

	@Test
	public void translateSubTest_WithOneParameter() {
		String vbsCode = "Sub Main(Parameter)\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateSub(vbsCode);

		Assert.assertEquals("function Main(Parameter) {\n}", jsCode);
	}
}
