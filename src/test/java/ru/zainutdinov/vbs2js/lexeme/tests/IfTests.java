package ru.zainutdinov.vbs2js.lexeme.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.If;
import ru.zainutdinov.vbs2js.word.IWord;

public class IfTests {

	@Test
	public void testJS_If() {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();
		List<IWord> expressionWords = new ArrayList<IWord>();
		expressionWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		expression.add(expressionWords);

		Words bodyLexemesWords = new Words("");
		bodyLexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something"));
		Lexemes bodyLexemes = new Lexemes(bodyLexemesWords);

		List<Lexemes> body = new ArrayList<Lexemes>();
		body.add(bodyLexemes);

		If if_ = new If(expression, body);

		Assert.assertEquals("if (Expression) {\n\tsomething\n}\n", if_.js(0));
	}

	@Test
	public void testJS_IfElse() {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();
		List<IWord> expressionWords = new ArrayList<IWord>();
		expressionWords.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		expression.add(expressionWords);

		Words body1LexemesWords = new Words("");
		body1LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something1"));
		Lexemes body1Lexemes = new Lexemes(body1LexemesWords);

		Words body2LexemesWords = new Words("");
		body2LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something2"));
		Lexemes body2Lexemes = new Lexemes(body2LexemesWords);

		List<Lexemes> body = new ArrayList<Lexemes>();
		body.add(body1Lexemes);
		body.add(body2Lexemes);

		If if_ = new If(expression, body);

		Assert.assertEquals("if (Expression) {\n\tsomething1\n}\nelse {\n\tsomething2\n}\n", if_.js(0));
	}

	@Test
	public void testJS_IfElseIfElse() {
		List<List<IWord>> expression = new ArrayList<List<IWord>>();

		List<IWord> expression1Words = new ArrayList<IWord>();
		expression1Words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression1"));
		expression.add(expression1Words);

		List<IWord> expression2Words = new ArrayList<IWord>();
		expression2Words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression2"));
		expression.add(expression2Words);

		Words body1LexemesWords = new Words("");
		body1LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something1"));
		Lexemes body1Lexemes = new Lexemes(body1LexemesWords);

		Words body2LexemesWords = new Words("");
		body2LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something2"));
		Lexemes body2Lexemes = new Lexemes(body2LexemesWords);

		Words body3LexemesWords = new Words("");
		body3LexemesWords.add(new ru.zainutdinov.vbs2js.word.Unknown("something3"));
		Lexemes body3Lexemes = new Lexemes(body3LexemesWords);

		List<Lexemes> body = new ArrayList<Lexemes>();
		body.add(body1Lexemes);
		body.add(body2Lexemes);
		body.add(body3Lexemes);

		If if_ = new If(expression, body);

		Assert.assertEquals(
				"if (Expression1) {\n\tsomething1\n}\nelse if (Expression2) {\n\tsomething2\n}\nelse {\n\tsomething3\n}\n",
				if_.js(0));
		Assert.assertEquals(
				"\tif (Expression1) {\n\t\tsomething1\n\t}\n\telse if (Expression2) {\n\t\tsomething2\n\t}\n\telse {\n\t\tsomething3\n\t}\n",
				if_.js(1));
	}
}