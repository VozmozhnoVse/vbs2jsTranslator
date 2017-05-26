package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.lexeme.Unknown;

public class UnknownTests {

	@Test
	public void testJS() {
		Unknown unknown = new Unknown("some text");

		Assert.assertEquals("some text\n", unknown.js(0));
	}

	@Test
	public void testJS_tab() {
		Unknown unknown = new Unknown("some text");

		Assert.assertEquals("\t\tsome text\n", unknown.js(2));
	}

	/*
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
	}*/
}