package ru.zainutdinov.vbs2js.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;
import ru.zainutdinov.vbs2js.lexeme.Unknown;
import ru.zainutdinov.vbs2js.word.Comma;
import ru.zainutdinov.vbs2js.word.IWord;
import ru.zainutdinov.vbs2js.word.ParenthesisClose;
import ru.zainutdinov.vbs2js.word.ParenthesisOpen;

public class LexemesTests {

	@Test
	public void test_Unknown() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("sometext"));

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(1, lexemes.size());

		ILexeme lexeme = lexemes.get(0);
		Assert.assertEquals(Unknown.class, lexeme.getClass());

		List<ru.zainutdinov.vbs2js.word.Unknown> lexemeWords = ((Unknown)lexeme).getWords();
		Assert.assertEquals(1, lexemeWords.size());
		Assert.assertEquals("sometext", lexemeWords.get(0).getText());
	}

	@Test
	public void test_PublicPrivate() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Public());
		words.add(new ru.zainutdinov.vbs2js.word.Private());

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(Public.class, lexemes.get(0).getClass());
		Assert.assertEquals(Private.class, lexemes.get(1).getClass());
	}

	@Test
	public void test_Sub() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(1, lexemes.size());

		ILexeme lexeme = lexemes.get(0);
		Assert.assertEquals(Sub.class, lexeme.getClass());

		ru.zainutdinov.vbs2js.word.Unknown name = ((Sub)lexeme).getName();
		Assert.assertEquals("SubName", name.getText());
	}

	@Test
	public void test_TwoSubs() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName1"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());
		words.add(new ru.zainutdinov.vbs2js.word.Sub());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("SubName2"));
		words.add(new ru.zainutdinov.vbs2js.word.EndSub());

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(2, lexemes.size());

		ILexeme lexeme1 = lexemes.get(0);
		ILexeme lexeme2 = lexemes.get(1);
		Assert.assertEquals(Sub.class, lexeme1.getClass());
		Assert.assertEquals(Sub.class, lexeme2.getClass());

		ru.zainutdinov.vbs2js.word.Unknown name1 = ((Sub)lexeme1).getName();
		ru.zainutdinov.vbs2js.word.Unknown name2 = ((Sub)lexeme2).getName();
		Assert.assertEquals("SubName1", name1.getText());
		Assert.assertEquals("SubName2", name2.getText());
	}

	@Test
	public void test_SubWithParametersAndBody() {
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

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(1, lexemes.size());

		ILexeme lexeme = lexemes.get(0);
		Assert.assertEquals(Sub.class, lexeme.getClass());

		ru.zainutdinov.vbs2js.word.Unknown name = ((Sub)lexeme).getName();
		Assert.assertEquals("SubName", name.getText());

		List<IWord> parameters = ((Sub)lexeme).getParameters();
		Assert.assertEquals(5, parameters.size());
		Assert.assertEquals(ParenthesisOpen.class, parameters.get(0).getClass());
		Assert.assertEquals("Parameter1", ((ru.zainutdinov.vbs2js.word.Unknown)parameters.get(1)).getText());
		Assert.assertEquals(Comma.class, parameters.get(2).getClass());
		Assert.assertEquals("Parameter2", ((ru.zainutdinov.vbs2js.word.Unknown)parameters.get(3)).getText());
		Assert.assertEquals(ParenthesisClose.class, parameters.get(4).getClass());

		List<ILexeme> body = ((Sub)lexeme).getBody();
		Assert.assertEquals(1, body.size());

		ILexeme bodyLexeme = body.get(0);
		Assert.assertEquals(Unknown.class, bodyLexeme.getClass());

		List<ru.zainutdinov.vbs2js.word.Unknown> lexemeWords = ((Unknown)bodyLexeme).getWords();
		Assert.assertEquals(1, lexemeWords.size());
		Assert.assertEquals("Body", lexemeWords.get(0).getText());
	}

	@Test
	public void test_Function() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.Return());
		words.add(new ru.zainutdinov.vbs2js.word.True());
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(1, lexemes.size());

		ILexeme lexeme = lexemes.get(0);
		Assert.assertEquals(Function.class, lexeme.getClass());

		ru.zainutdinov.vbs2js.word.Unknown name = ((Function)lexeme).getName();
		Assert.assertEquals("FunctionName", name.getText());
	}

	@Test
	public void test_TwoFunctions() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName1"));
		words.add(new ru.zainutdinov.vbs2js.word.Return());
		words.add(new ru.zainutdinov.vbs2js.word.True());
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName2"));
		words.add(new ru.zainutdinov.vbs2js.word.Return());
		words.add(new ru.zainutdinov.vbs2js.word.False());
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(2, lexemes.size());

		ILexeme lexeme1 = lexemes.get(0);
		ILexeme lexeme2 = lexemes.get(1);
		Assert.assertEquals(Function.class, lexeme1.getClass());
		Assert.assertEquals(Function.class, lexeme2.getClass());

		ru.zainutdinov.vbs2js.word.Unknown name1 = ((Function)lexeme1).getName();
		ru.zainutdinov.vbs2js.word.Unknown name2 = ((Function)lexeme2).getName();
		Assert.assertEquals("FunctionName1", name1.getText());
		Assert.assertEquals("FunctionName2", name2.getText());
	}

	@Test
	public void test_FunctionWithParametersAndBody() {
		List<IWord> words = new ArrayList<IWord>();
		words.add(new ru.zainutdinov.vbs2js.word.Function());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("FunctionName"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter1"));
		words.add(new ru.zainutdinov.vbs2js.word.Comma());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Parameter2"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Body"));
		words.add(new ru.zainutdinov.vbs2js.word.Return());
		words.add(new ru.zainutdinov.vbs2js.word.True());
		words.add(new ru.zainutdinov.vbs2js.word.EndFunction());

		
		List<ILexeme> lexemes = new Lexemes(words).getLexemes();

		Assert.assertEquals(1, lexemes.size());

		ILexeme lexeme = lexemes.get(0);
		Assert.assertEquals(Function.class, lexeme.getClass());

		ru.zainutdinov.vbs2js.word.Unknown name = ((Function)lexeme).getName();
		Assert.assertEquals("FunctionName", name.getText());

		List<IWord> parameters = ((Function)lexeme).getParameters();
		Assert.assertEquals(5, parameters.size());
		Assert.assertEquals(ParenthesisOpen.class, parameters.get(0).getClass());
		Assert.assertEquals("Parameter1", ((ru.zainutdinov.vbs2js.word.Unknown)parameters.get(1)).getText());
		Assert.assertEquals(Comma.class, parameters.get(2).getClass());
		Assert.assertEquals("Parameter2", ((ru.zainutdinov.vbs2js.word.Unknown)parameters.get(3)).getText());
		Assert.assertEquals(ParenthesisClose.class, parameters.get(4).getClass());


		List<ILexeme> body = ((Function)lexeme).getBody();
		Assert.assertEquals(1, body.size());

		ILexeme bodyLexeme = body.get(0);
		Assert.assertEquals(Unknown.class, bodyLexeme.getClass());

		List<ru.zainutdinov.vbs2js.word.Unknown> lexemeWords = ((Unknown)bodyLexeme).getWords();
		Assert.assertEquals(1, lexemeWords.size());
		Assert.assertEquals("Body", lexemeWords.get(0).getText());
	}
}