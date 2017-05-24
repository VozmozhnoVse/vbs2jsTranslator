package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Private;
import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.Sub;

public class LexemesTests {

	@Test
	public void test() {
		Lexemes lexemes = new Lexemes();
		
		lexemes.add(new Public());
		lexemes.add(new Sub("Main", "", ""));

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Public(), lexemes.get(0));
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(1));
	}

	@Test
	public void testJS_PublicMain() {
		Lexemes lexemes = new Lexemes();
		
		lexemes.add(new Public());
		lexemes.add(new Sub("Main", "", ""));

		Assert.assertEquals("function Main() {\n}\n", lexemes.js());
	}

	@Test
	public void testJS_PrivateMain() {
		Lexemes lexemes = new Lexemes();

		lexemes.add(new Private());
		lexemes.add(new Sub("Main", "", ""));

		Assert.assertEquals("function Main() {\n}\n", lexemes.js());
	}

	@Test
	public void testJS_TwoSimpleSub() {
		Lexemes lexemes = new Lexemes();

		lexemes.add(new Sub("Main1", "", ""));
		lexemes.add(new Sub("Main2", "", ""));

		Assert.assertEquals("function Main1() {\n}\nfunction Main2() {\n}\n", lexemes.js());
	}
}