package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateTrueTests {

	@Test
	public void translateTrueTest_Camelcase() {
		String vbsCode = "Object1.Property = True";

		String jsCode = new Vbs2JsTranslator().translateTrue(vbsCode);

		Assert.assertEquals("Object1.Property = true", jsCode);
	}

	@Test
	public void translateTrueTest_Uppercase() {
		String vbsCode = "Object1.Property = TRUE";

		String jsCode = new Vbs2JsTranslator().translateTrue(vbsCode);

		Assert.assertEquals("Object1.Property = true", jsCode);
	}
}
