package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Converter;

public class ConverterTests {

	@Test
	public void testJS_usualCase() {
		String vbs = FileUtils.readFileToString("usualCase.vbs");
		String jsExpected = FileUtils.readFileToString("usualCase.js");

		String jsActual = new Converter(vbs).js();

		Assert.assertEquals(jsExpected, jsActual);
	}
}
