package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.If;

public class IfTests {

	@Test
	public void testJS_IfThen() {
		If if_ = new If(new String[] {"(true)"}, new String[] {"some_correct_text;"});

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n}\n", if_.js());
	}
	
	@Test
	public void testJS_IfThenElse() {
		If if_ = new If(new String[] {"(true)"}, new String[] {"some_correct_text;", "another_correct_text;"});

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n} else {\n\tanother_correct_text;\n}\n", if_.js());
	}

	@Test
	public void testJS_IfThenElseIf() {
		If if_ = new If(new String[] {"(true)", "(false)"}, new String[] {"some_correct_text;", "another_correct_text;"});

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n} else if (false) {\n\tanother_correct_text;\n}\n", if_.js());
	}

	@Test
	public void testJS_IfThenElseIfElse() {
		If if_ = new If(new String[] {"(true)", "(false)"}, new String[] {"some_correct_text;", "another_correct_text1;", "another_correct_text2;"});

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n} else if (false) {\n\tanother_correct_text1;\n} else {\n\tanother_correct_text2;\n}\n", if_.js());
	}

	@Test
	public void testEquals_true() {
		Assert.assertEquals(new If(new String[] {"(true)"}, new String[] {"some_correct_text;", "another_correct_text;"}), new If(new String[] {"(true)"}, new String[] {"some_correct_text;", "another_correct_text;"}));
	}

	@Test
	public void testEquals_false() {
		Assert.assertNotEquals(new If(new String[] {"(true)"}, new String[] {"some_correct_text;", "another_correct_text;"}), new If(new String[] {"(false)"}, new String[] {"some_correct_text;", "another_correct_text;"}));
	}

	@Test
	public void testEquals_falseAnotherType() {
		Assert.assertNotEquals(new If(new String[] {"(true)"}, new String[] {"some_correct_text;", "another_correct_text;"}), new Public());
	}
}
