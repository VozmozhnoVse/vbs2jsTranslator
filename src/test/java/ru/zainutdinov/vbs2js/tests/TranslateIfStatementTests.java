package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateIfStatementTests {
	@Test
	public void translateIfStatementTest_IfElse() {
		String vbsCode = "If (True) Then\nElse\nEnd If";

		String jsCode = new Vbs2JsTranslator().translateIfStatement(vbsCode);

		Assert.assertEquals("if (true) {\n} else {\n}", jsCode);
	}

	@Test
	public void translateIfStatementTest_IfElseIfElse() {
		String vbsCode = "If (True) Then\nElseIf (False) Then\nElse\nEnd If";

		String jsCode = new Vbs2JsTranslator().translateIfStatement(vbsCode);

		Assert.assertEquals("if (true) {\n} else if (false) {\n} else {\n}", jsCode);
	}
}
