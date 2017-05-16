package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.translator.Vbs2JsTranslator;

public class TranslateFunctionTests {

	@Test
	public void translateFunctionTest_Simple() {
		String vbsCode = "Function Main\n\tMain = true\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateFunction(vbsCode);

		Assert.assertEquals("function Main() {\n\treturn true;\n}", jsCode);
	}

	@Test
	public void translateFunctionTest_TwoSimple() {
		String vbsCode = "Function Main1\n\tMain1 = true\nEnd Sub\nFunction Main2\n\tMain2 = true\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateFunction(vbsCode);

		Assert.assertEquals("function Main1() {\n\treturn true;\n}\nfunction Main2() {\n\treturn true;\n}", jsCode);
	}

	@Test
	public void translateFunctionTest_Public() {
		String vbsCode = "Public Function Main\n\tMain = true\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateFunction(vbsCode);

		Assert.assertEquals("function Main() {\n\treturn true;\n}", jsCode);
	}

	@Test
	public void translateFunctionTest_Private() {
		String vbsCode = "Private Function Main\n\tMain = true\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateFunction(vbsCode);

		Assert.assertEquals("function Main() {\n\treturn true;\n}", jsCode);
	}

	@Test
	public void translateFunctionTest_WithOneParameterAndTwoReturns() {
		String vbsCode = "Function Main(Parameter)\n\tif (Parameter) then\n\t\tMain = true\n\telse\n\t\tMain = false\n\tend if\nEnd Sub";

		String jsCode = new Vbs2JsTranslator().translateFunction(vbsCode);

		Assert.assertEquals("function Main(Parameter) {\n\tif (Parameter) then\n\t\treturn true;\n\telse\n\t\treturn false;\n\tend if\n}", jsCode);
	}
}
