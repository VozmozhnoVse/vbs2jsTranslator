package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Words;

public class LexemesTests {

	@Test
	public void testJS_Unknown() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("sometext"));

		String js = new Lexemes(words).js();

		Assert.assertEquals("sometext\n", js);
	}

	@Test
	public void testJS_PublicPrivateReturnTrueFalse() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Public());
		words.add(new ru.zainutdinov.vbs2js.word.Private());
		words.add(new ru.zainutdinov.vbs2js.word.True());
		words.add(new ru.zainutdinov.vbs2js.word.False());

		String js = new Lexemes(words).js();

		Assert.assertEquals("true\nfalse\n", js);
	}

	@Test
	public void testJS_Sub() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function SubName() {\n}\n", js);
	}

	@Test
	public void testJS_TwoSubs() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName1"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName2"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function SubName1() {\n}\nfunction SubName2() {\n}\n", js);
	}

	@Test
	public void testJS_SubWithParametersAndBody() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function SubName(Parameters) {\n\tBody\n}\n", js);
	}

	@Test
	public void testJS_Function() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function FunctionName() {\n}\n", js);
	}

	@Test
	public void testJS_TwoFunctions() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName1"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName2"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function FunctionName1() {\n}\nfunction FunctionName2() {\n}\n", js);
	}

	@Test
	public void testJS_FunctionWithParametersAndBody() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameters"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function FunctionName(Parameters) {\n\tBody\n}\n", js);
	}

	@Test
	public void testJS_If() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.If());
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		String js = new Lexemes(words).js();

		Assert.assertEquals("if (Expression) {\n\tBody\n}\n", js);
	}

	@Test
	public void testJS_IfElse() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.If());
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body1"));
		words.add(new ru.zainutdinov.vbs2js.word.Else());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body2"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		String js = new Lexemes(words).js();

		Assert.assertEquals("if (Expression) {\n\tBody1\n}\nelse {\n\tBody2\n}\n", js);
	}

	@Test
	public void testJS_IfElseIfElse() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.If());
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression1"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body1"));
		words.add(new ru.zainutdinov.vbs2js.word.ElseIf());
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression2"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body2"));
		words.add(new ru.zainutdinov.vbs2js.word.Else());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body3"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		String js = new Lexemes(words).js();

		Assert.assertEquals("if (Expression1) {\n\tBody1\n}\nelse if (Expression2) {\n\tBody2\n}\nelse {\n\tBody3\n}\n",
				js);
	}
}