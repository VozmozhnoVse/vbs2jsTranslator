package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.If;

public class IfTests {

	@Test
	public void testJS_simple() {
		If if_ = new If("(true)", "some_correct_text;");

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n}\n", if_.js());
	}

	@Test
	public void testEquals_true() {
		Assert.assertEquals(new If("(true)", "some_correct_text;"), new If("(true)", "some_correct_text;"));
	}

	@Test
	public void testEquals_false() {
		Assert.assertNotEquals(new If("(true)", "some_correct_text;"), new If("(false)", "some_correct_text;"));
	}

	@Test
	public void testEquals_falseAnotherType() {
		Assert.assertNotEquals(new If("(true)", "some_correct_text;"), new Public());
	}
}
