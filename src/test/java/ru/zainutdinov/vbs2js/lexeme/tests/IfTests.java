package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import ru.zainutdinov.vbs2js.lexeme.ILexeme;
import ru.zainutdinov.vbs2js.lexeme.If;
import ru.zainutdinov.vbs2js.lexeme.Unknown;
import ru.zainutdinov.vbs2js.word.False;
import ru.zainutdinov.vbs2js.word.IWord;
import ru.zainutdinov.vbs2js.word.True;

public class IfTests {

	@Test
	public void testJS_If() {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();
		List<IWord> expressionWords = new ArrayList<IWord>();
		expressionWords.add(new True());
		expression.add(expressionWords);

		List<List<ILexeme>> body = new ArrayList<List<ILexeme>>();
		List<ILexeme> bodyLexemes = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> bodyLexemesWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		bodyLexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something"));
		bodyLexemes.add(new Unknown(bodyLexemesWords));
		body.add(bodyLexemes);

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsomething\n}\n", if_.js(0));
	}

	@Test
	public void testJS_IfElse() {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();
		List<IWord> expressionWords = new ArrayList<IWord>();
		expressionWords.add(new True());
		expression.add(expressionWords);

		List<List<ILexeme>> body = new ArrayList<List<ILexeme>>();

		List<ILexeme> body1Lexemes = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> body1LexemesWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		body1LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something1"));
		body1Lexemes.add(new Unknown(body1LexemesWords));
		body.add(body1Lexemes);

		List<ILexeme> body2Lexemes = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> body2LexemesWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		body2LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something2"));
		body2Lexemes.add(new Unknown(body2LexemesWords));
		body.add(body2Lexemes);

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsomething1\n}\nelse {\n\tsomething2\n}\n", if_.js(0));
	}

	@Test
	public void testJS_IfElseIfElse() {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();

		List<IWord> expression1Words = new ArrayList<IWord>();
		expression1Words.add(new True());
		expression.add(expression1Words);

		List<IWord> expression2Words = new ArrayList<IWord>();
		expression2Words.add(new False());
		expression.add(expression2Words);
		
		List<List<ILexeme>> body = new ArrayList<List<ILexeme>>();

		List<ILexeme> body1Lexemes = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> body1LexemesWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		body1LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something1"));
		body1Lexemes.add(new Unknown(body1LexemesWords));
		body.add(body1Lexemes);

		List<ILexeme> body2Lexemes = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> body2LexemesWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		body2LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something2"));
		body2Lexemes.add(new Unknown(body2LexemesWords));
		body.add(body2Lexemes);

		List<ILexeme> body3Lexemes = new ArrayList<ILexeme>();
		List<ru.zainutdinov.vbs2js.word.Unknown> body3LexemesWords = new ArrayList<ru.zainutdinov.vbs2js.word.Unknown>();
		body3LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something3"));
		body3Lexemes.add(new Unknown(body3LexemesWords));
		body.add(body3Lexemes);

		If if_ = new If(expression, body);

		Assert.assertEquals("if (true) {\n\tsomething1\n}\nelse if (false) {\n\tsomething2\n}\nelse {\n\tsomething3\n}\n", if_.js(0));
		Assert.assertEquals("\tif (true) {\n\t\tsomething1\n\t}\n\telse if (false) {\n\t\tsomething2\n\t}\n\telse {\n\t\tsomething3\n\t}\n", if_.js(1));
	}
}