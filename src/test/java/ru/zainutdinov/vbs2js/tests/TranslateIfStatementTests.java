package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateIfStatementTests {
	@Test
	public void translateSubTest_Simple() {
		String vbsCode = "If (True) Then\nEnd If";

		String jsCode = new Vbs2JsTranslator().translateIfStatement(vbsCode);

		Assert.assertEquals("if (true) {\n}", jsCode);
	}
}
