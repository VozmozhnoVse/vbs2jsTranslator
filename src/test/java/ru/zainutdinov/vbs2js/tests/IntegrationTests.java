package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.VBS;

public class IntegrationTests {

	@Test
	public void test_usualCase() {
		VBS vbs = new VBS(FileUtilities.readFileToString("usualCase.vbs"));

		String jsActual = vbs.lexemes().js();
		String jsExpected = FileUtilities.readFileToString("usualCase.js");

		Assert.assertEquals(jsExpected, jsActual);;
	}
}
