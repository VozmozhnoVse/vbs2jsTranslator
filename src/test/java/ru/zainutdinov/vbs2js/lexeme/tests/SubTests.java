package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;
import ru.zainutdinov.vbs2js.lexeme.Unknown;

public class SubTests {

	@Test
	public void testJS_emptyParametersList() {
		Sub sub = new Sub("Main", "", null);

		Assert.assertEquals("function Main() {\n}\n", sub.js(0));
	}
	
	@Test
	public void testJS_threeParameters() {
		Sub sub = new Sub("Main", "parameter1, parameter2, parameter3", null);

		Assert.assertEquals("function Main(parameter1, parameter2, parameter3) {\n}\n", sub.js(0));
	}

	@Test
	public void testJS_body() {
		Lexemes lexemes = new Lexemes();
		lexemes.add(new Unknown("some_correct_text;"));
		Sub sub = new Sub("Main", "", lexemes);

		Assert.assertEquals("function Main() {\n\tsome_correct_text;\n}\n", sub.js(0));
	}

	@Test
	public void testJS_Tab() {
		Lexemes lexemes = new Lexemes();
		lexemes.add(new Unknown("some_correct_text;"));
		Sub sub = new Sub("Main", "", lexemes);

		Assert.assertEquals("\tfunction Main() {\n\t\tsome_correct_text;\n\t}\n", sub.js(1));
	}

	@Test
	public void testEquals_true() {
		Lexemes lexemes = new Lexemes();
		lexemes.add(new Unknown("some_correct_text;"));
		Assert.assertEquals(new Sub("Main", "parameter", lexemes), new Sub("Main", "parameter", lexemes));
	}

	@Test
	public void testEquals_false() {
		Lexemes lexemes = new Lexemes();
		lexemes.add(new Unknown("some_correct_text;"));
		Assert.assertNotEquals(new Sub("Main1", "parameter", lexemes), new Sub("Main", "parameter", lexemes));
	}

	@Test
	public void testEquals_falseAnotherType() {
		Lexemes lexemes = new Lexemes();
		lexemes.add(new Unknown("some_correct_text;"));
		Assert.assertNotEquals(new Sub("Main", "parameter", lexemes), new Public());
	}
}
