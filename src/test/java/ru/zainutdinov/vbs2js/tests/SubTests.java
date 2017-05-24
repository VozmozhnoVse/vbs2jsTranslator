package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.Sub;

public class SubTests {

	@Test
	public void testJS_emptyParametersList() {
		Sub sub = new Sub("Main", "", "");

		Assert.assertEquals("function Main() {\n}\n", sub.js());
	}
	
	@Test
	public void testJS_threeParameters() {
		Sub sub = new Sub("Main", "parameter1, parameter2, parameter3", "");

		Assert.assertEquals("function Main(parameter1, parameter2, parameter3) {\n}\n", sub.js());
	}

	@Test
	public void testJS_body() {
		Sub sub = new Sub("Main", "", "some_correct_text;");

		Assert.assertEquals("function Main() {\n\tsome_correct_text;\n}\n", sub.js());
	}

	@Test
	public void testEquals_true() {
		Assert.assertEquals(new Sub("Main", "parameter", "some_correct_text;"), new Sub("Main", "parameter", "some_correct_text;"));
	}

	@Test
	public void testEquals_false() {
		Assert.assertNotEquals(new Sub("Main1", "parameter", "some_correct_text;"), new Sub("Main", "parameter", "some_correct_text;"));
	}

	@Test
	public void testEquals_falseAnotherType() {
		Assert.assertNotEquals(new Sub("Main", "parameter", "some_correct_text;"), new Public());
	}
}
