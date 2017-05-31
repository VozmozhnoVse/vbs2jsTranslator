package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.word.IWord;

public class LexemesTests {

	@Test
	public void testJS_Unknown() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("sometext"));

		String js = new Lexemes(words).js();

		Assert.assertEquals("sometext\n", js);
	}

	@Test
	public void testJS_PublicPrivateReturnTrueFalse() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Public());
		words.add(new ru.zainutdinov.vbs2js.word.Private());
		words.add(new ru.zainutdinov.vbs2js.word.True());
		words.add(new ru.zainutdinov.vbs2js.word.False());

		String js = new Lexemes(words).js();

		Assert.assertEquals("true\nfalse\n", js);
	}

	@Test
	public void testJS_Sub() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function SubName() {\n}\n", js);
	}

	@Test
	public void testJS_TwoSubs() {
		List<IWord> words = new ArrayList<IWord>();
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
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter1"));
		words.add(new ru.zainutdinov.vbs2js.word.Comma());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter2"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function SubName((Parameter1, Parameter2)) {\n\tBody\n}\n", js);
	}

	@Test
	public void testJS_Function() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function FunctionName() {\n}\n", js);
	}

	@Test
	public void testJS_TwoFunctions() {
		List<IWord> words = new ArrayList<IWord>();
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
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter1"));
		words.add(new ru.zainutdinov.vbs2js.word.Comma());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter2"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		String js = new Lexemes(words).js();

		Assert.assertEquals("function FunctionName((Parameter1, Parameter2)) {\n\tBody\n}\n", js);
	}

	@Test
	public void testJS_If() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.If());
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		String js = new Lexemes(words).js();

		Assert.assertEquals("if ((Expression)) {\n\tBody\n}\n", js);
	}

	@Test
	public void testJS_IfElse() {
		List<IWord> words = new ArrayList<IWord>();
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

		Assert.assertEquals("if ((Expression)) {\n\tBody1\n}\nelse {\n\tBody2\n}\n", js);
	}

	@Test
	public void testJS_IfElseIfElse() {
		List<IWord> words = new ArrayList<IWord>();
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

		Assert.assertEquals(
				"if ((Expression1)) {\n\tBody1\n}\nelse if ((Expression2)) {\n\tBody2\n}\nelse {\n\tBody3\n}\n", js);
	}
}