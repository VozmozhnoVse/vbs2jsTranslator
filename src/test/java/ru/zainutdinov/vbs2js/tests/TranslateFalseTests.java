package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateFalseTests {

	@Test
	public void translateFalseTest_Camelcase() {
		String vbsCode = "Object1.Property = False";

		String jsCode = new Vbs2JsTranslator().translateFalse(vbsCode);

		Assert.assertEquals("Object1.Property = false", jsCode);
	}

	@Test
	public void translateFalseTest_Uppercase() {
		String vbsCode = "Object1.Property = FALSE";

		String jsCode = new Vbs2JsTranslator().translateFalse(vbsCode);

		Assert.assertEquals("Object1.Property = false", jsCode);
	}
}
