package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.If;

public class IfTests {

	@Test
	public void testJS_IfThen() {
		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		body.add("some_correct_text;");

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n}\n", if_.js());
	}
	
	@Test
	public void testJS_IfThenElse() {
		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		body.add("some_correct_text;");
		body.add("another_correct_text;");

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n} else {\n\tanother_correct_text;\n}\n", if_.js());
	}

	@Test
	public void testJS_IfThenElseIf() {
		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		expression.add("(false)");
		body.add("some_correct_text;");
		body.add("another_correct_text;");

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n} else if (false) {\n\tanother_correct_text;\n}\n", if_.js());
	}

	@Test
	public void testJS_IfThenElseIfElse() {
		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		expression.add("(false)");
		body.add("some_correct_text;");
		body.add("another_correct_text1;");
		body.add("another_correct_text2;");

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsome_correct_text;\n} else if (false) {\n\tanother_correct_text1;\n} else {\n\tanother_correct_text2;\n}\n", if_.js());
	}

	@Test
	public void testEquals_true() {
		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		body.add("some_correct_text;");
		body.add("another_correct_text;");

		Assert.assertEquals(new If(expression, body), new If(expression, body));
	}

	@Test
	public void testEquals_false() {
		List<String> expression1 = new ArrayList<String>();
		List<String> expression2 = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression1.add("(true)");
		expression2.add("(false)");
		body.add("some_correct_text;");
		body.add("another_correct_text;");

		Assert.assertNotEquals(new If(expression1, body), new If(expression2, body));
	}

	@Test
	public void testEquals_falseAnotherType() {
		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		body.add("some_correct_text;");
		body.add("another_correct_text;");

		Assert.assertNotEquals(new If(expression, body), new Public());
	}
}
