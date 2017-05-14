package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class SubTests {

	@Test
	public void SubWithOneParameterTest() {
		String vbsCode = "Sub Main(Parameter)\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateSub(vbsCode);

		Assert.assertEquals("function Main(Parameter) {\n}", jsCode);
	}
}
