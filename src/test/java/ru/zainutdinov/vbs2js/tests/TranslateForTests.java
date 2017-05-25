package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateForTests {

	@Test
	public void translateForTest_Simple() {
		String vbsCode = "For i = 0 To 10\n\tsomecode\nNext";

		// TODO 	String jsCode = new Vbs2JsTranslator().translateFor(vbsCode);

		// TODO 	Assert.assertEquals("for (var i = 0; i < 10; i++) {\n\tsomecode\n}", jsCode);
	}

	@Test
	public void translateForTest_Step() {
		String vbsCode = "For i = 0 To 10 Step 2\n\tsomecode\nNext";

		// TODO 	String jsCode = new Vbs2JsTranslator().translateFor(vbsCode);

		// TODO 	Assert.assertEquals("for (var i = 0; i < 10; i += 2) {\n\tsomecode\n}", jsCode);
	}

	@Test
	public void translateForTest_ExitFor() {
		String vbsCode = "For i = 0 To 10 Step 2\n\tExit For\nNext";

		// TODO 	String jsCode = new Vbs2JsTranslator().translateFor(vbsCode);

		// TODO 	Assert.assertEquals("for (var i = 0; i < 10; i += 2) {\n\tbreak;\n}", jsCode);
	}
}