package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Function;
import ru.zainutdinov.vbs2js.If;
import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Private;
import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.Sub;
import ru.zainutdinov.vbs2js.VBS;

public class VBSTests {

	@Test
	public void testLexemes_Sub() {
		VBS vbs = new VBS("Sub Main\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();
		
		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(0));
	}

	@Test
	public void testLexemes_TwoSubs() {
		VBS vbs = new VBS("Sub Main1\nEnd Sub\nSub Main2\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Sub("Main1", "", ""), lexemes.get(0));
		Assert.assertEquals(new Sub("Main2", "", ""), lexemes.get(1));
	}

	@Test
	public void testLexemes_SubWithPublic() {
		VBS vbs = new VBS("Public Sub Main\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Public(), lexemes.get(0));
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(1));
	}

	@Test
	public void testLexemes_SubWithPrivate() {
		VBS vbs = new VBS("Private Sub Main\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Private(), lexemes.get(0));
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(1));
	}

	@Test
	public void testLexemes_SubWithParameters() {
		VBS vbs = new VBS("Sub Main(Parameter1, Parameter2)\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Sub("Main", "Parameter1, Parameter2", ""), lexemes.get(0));
	}

	@Test
	public void testLexemes_Function() {
		VBS vbs = new VBS("Function Main\n\tMain = true\nEnd Function");

		Lexemes lexemes = vbs.lexemes();
		
		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Function("Main", "", "return true;"), lexemes.get(0));
	}

	@Test
	public void testLexemes_TwoFunctios() {
		VBS vbs = new VBS("Function Main1\n\tMain1 = true\nEnd Function\nFunction Main2\n\tMain2 = true\nEnd Function");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Function("Main1", "", "return true;"), lexemes.get(0));
		Assert.assertEquals(new Function("Main2", "", "return true;"), lexemes.get(1));
	}

	@Test
	public void testLexemes_FunctionWithPublic() {
		VBS vbs = new VBS("Public Function Main\n\tMain = true\nEnd Function");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Public(), lexemes.get(0));
		Assert.assertEquals(new Function("Main", "", "return true;"), lexemes.get(1));
	}

	@Test
	public void testLexemes_FunctionWithPrivate() {
		VBS vbs = new VBS("Private Function Main\n\tMain = true\nEnd Function");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Private(), lexemes.get(0));
		Assert.assertEquals(new Function("Main", "", "return true;"), lexemes.get(1));
	}

	@Test
	public void testLexemes_FunctionWithParameters() {
		VBS vbs = new VBS("Function Main(Parameter1, Parameter2)\n\tMain = true\nEnd Function");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Function("Main", "Parameter1, Parameter2", "return true;"), lexemes.get(0));
	}
	
	@Test
	public void testLexemes_FunctionWithOneParameterAndTwoReturns() {
		VBS vbs = new VBS("Function Main(Parameter)\n\tif (Parameter) then\n\t\tMain = true\n\telse\n\t\tMain = false\n\tendif\nEnd Function");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Function("Main", "Parameter", "if (Parameter) then\nreturn true;\nelse\nreturn false;\nendif"), lexemes.get(0));
	}

	//TODO: test for function when "main=true; some_text" and we cannot simple replace "main=true;" to "return true;"...
	
	@Test
	public void testLexemes_Public() {
		VBS vbs = new VBS("Public");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Public(), lexemes.get(0));
	}

	@Test
	public void testLexemes_Private() {
		VBS vbs = new VBS("Private");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Private(), lexemes.get(0));
	}

	@Test
	public void testLexemes_IfThenEndif() {
		VBS vbs = new VBS("If (true) Then\n\tsome_correct_text\nEnd If");

		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();
		
		expression.add("(true)");
		body.add("some_correct_text");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new If(expression, body), lexemes.get(0));
	}

	@Test
	public void testLexemes_IfThenElseEndif() {
		VBS vbs = new VBS("If (true) Then\n\tsome_correct_text\nElse\n\tanother_correct_text\nEnd If");

		List<String> expression = new ArrayList<String>();
		List<String> body = new ArrayList<String>();

		expression.add("(true)");
		body.add("some_correct_text");
		body.add("another_correct_text");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new If(expression, body), lexemes.get(0));
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

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new If(expression, body), lexemes.get(0));
	}
}