package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.VBS;
import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.If;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;

public class VBSTests {

	@Test
	public void testLexemes_Sub() {
		VBS vbs = new VBS("Sub Main\nEnd Sub");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Sub("Main", "", null));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_TwoSubs() {
		VBS vbs = new VBS("Sub Main1\nEnd Sub\nSub Main2\nEnd Sub");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Sub("Main1", "", null));
		expected.add(new Sub("Main2", "", null));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_SubWithPublic() {
		VBS vbs = new VBS("Public Sub Main\nEnd Sub");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Sub("Main", "", null));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_SubWithPrivate() {
		VBS vbs = new VBS("Private Sub Main\nEnd Sub");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Private());
		expected.add(new Sub("Main", "", null));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_SubWithParameters() {
		VBS vbs = new VBS("Sub Main(Parameter1, Parameter2)\nEnd Sub");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Sub("Main", "Parameter1, Parameter2", null));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_Function() {
		VBS vbs = new VBS("Function Main\n\tMain = true\nEnd Function");

		Lexemes actual = vbs.lexemes();
		
		Lexemes expected = new Lexemes();
		expected.add(new Function("Main", "", "return true;"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_TwoFunctios() {
		VBS vbs = new VBS("Function Main1\n\tMain1 = true\nEnd Function\nFunction Main2\n\tMain2 = true\nEnd Function");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Function("Main1", "", "return true;"));
		expected.add(new Function("Main2", "", "return true;"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_FunctionWithPublic() {
		VBS vbs = new VBS("Public Function Main\n\tMain = true\nEnd Function");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Public());
		expected.add(new Function("Main", "", "return true;"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_FunctionWithPrivate() {
		VBS vbs = new VBS("Private Function Main\n\tMain = true\nEnd Function");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Private());
		expected.add(new Function("Main", "", "return true;"));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_FunctionWithParameters() {
		VBS vbs = new VBS("Function Main(Parameter1, Parameter2)\n\tMain = true\nEnd Function");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Function("Main", "Parameter1, Parameter2", "return true;"));
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testLexemes_FunctionWithOneParameterAndTwoReturns() {
		VBS vbs = new VBS("Function Main(Parameter)\n\tif (Parameter) then\n\t\tMain = true\n\telse\n\t\tMain = false\n\tendif\nEnd Function");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Function("Main", "Parameter", "if (Parameter) then\nreturn true;\nelse\nreturn false;\nendif"));
		Assert.assertEquals(expected, actual);
	}

	//TODO: test for function when "main=true; some_text" and we cannot simple replace "main=true;" to "return true;"...
	
	@Test
	public void testLexemes_Public() {
		VBS vbs = new VBS("Public");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Public());
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_Private() {
		VBS vbs = new VBS("Private");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new Private());
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_IfThenEndif() {
		VBS vbs = new VBS("If (true) Then\n\tsome_correct_text\nEnd If");

		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();
		
		expression.add("(true)");
		body.add("some_correct_text");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new If(expression, body));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_IfThenElseEndif() {
		VBS vbs = new VBS("If (true) Then\n\tsome_correct_text\nElse\n\tanother_correct_text\nEnd If");

		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		body.add("some_correct_text");
		body.add("another_correct_text");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new If(expression, body));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLexemes_IfThenElseIfElseEndif() {
		VBS vbs = new VBS("If (true) Then\n\tsome_correct_text\nElseIf (false) Then\n\tanother_correct_text1\nElse\n\tanother_correct_text2\nEnd If");

		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		expression.add("(false)");
		body.add("some_correct_text");
		body.add("another_correct_text1");
		body.add("another_correct_text2");

		Lexemes actual = vbs.lexemes();

		Lexemes expected = new Lexemes();
		expected.add(new If(expression, body));
		Assert.assertEquals(expected, actual);
	}
}