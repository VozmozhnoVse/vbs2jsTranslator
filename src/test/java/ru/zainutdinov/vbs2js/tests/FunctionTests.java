package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.Function;

public class FunctionTests {

	@Test
	public void testJS_emptyParametersList() {
		Function function = new Function("Main", "", "return true;");

		Assert.assertEquals("function Main() {\n\treturn true;\n}\n", function.js());
	}
	
	@Test
	public void testJS_threeParameters() {
		Function function = new Function("Main", "parameter1, parameter2, parameter3", "return true;");

		Assert.assertEquals("function Main(parameter1, parameter2, parameter3) {\n\treturn true;\n}\n", function.js());
	}

	@Test
	public void testEquals_true() {
		Assert.assertEquals(new Function("Main", "parameter", "some_correct_text;"), new Function("Main", "parameter", "some_correct_text;"));
	}

	@Test
	public void testEquals_false() {
		Assert.assertNotEquals(new Function("Main1", "parameter", "some_correct_text;"), new Function("Main", "parameter", "some_correct_text;"));
	}

	@Test
	public void testEquals_falseAnotherType() {
		Assert.assertNotEquals(new Function("Main", "parameter", "some_correct_text;"), new Public());
	}
}