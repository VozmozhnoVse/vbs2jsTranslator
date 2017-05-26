package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.lexeme.Function;
import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;
import ru.zainutdinov.vbs2js.lexeme.Sub;

public class LexemesTests {

	@Test
	public void test() {
		Lexemes actual = new Lexemes();
		actual.add(new Public());
		actual.add(new Sub("Main", "", null));

		Lexemes expected = new Lexemes();
		expected.add(new Public());
		expected.add(new Sub("Main", "", null));

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testJS_SubWithPublic() {
		Lexemes lexemes = new Lexemes();
		
		lexemes.add(new Public());
		lexemes.add(new Sub("Main", "", null));

		Assert.assertEquals("function Main() {\n}\n", lexemes.js(0));
	}

	@Test
	public void testJS_SubWithPrivate() {
		Lexemes lexemes = new Lexemes();

		lexemes.add(new Private());
		lexemes.add(new Sub("Main", "", null));

		Assert.assertEquals("function Main() {\n}\n", lexemes.js(0));
	}

	@Test
	public void testJS_SubsTwo() {
		Lexemes lexemes = new Lexemes();

		lexemes.add(new Sub("Main1", "", null));
		lexemes.add(new Sub("Main2", "", null));

		Assert.assertEquals("function Main1() {\n}\nfunction Main2() {\n}\n", lexemes.js(0));
	}

	@Test
	public void testJS_FunctionWithPublic() {
		Lexemes lexemes = new Lexemes();
		
		lexemes.add(new Public());
		lexemes.add(new Function("Main", "", "return true;"));

		Assert.assertEquals("function Main() {\n\treturn true;\n}\n", lexemes.js(0));
	}

	@Test
	public void testJS_Tab() {
		Lexemes lexemes = new Lexemes();
		
		lexemes.add(new Public());
		lexemes.add(new Function("Main", "", "return true;"));

		Assert.assertEquals("\tfunction Main() {\n\t\treturn true;\n\t}\n", lexemes.js(1));
	}

	@Test
	public void testJS_FunctionWithPrivate() {
		Lexemes lexemes = new Lexemes();

		lexemes.add(new Private());
		lexemes.add(new Function("Main", "", "return true;"));

		Assert.assertEquals("function Main() {\n\treturn true;\n}\n", lexemes.js(0));
	}

	@Test
	public void testJS_FunctionsTwo() {
		Lexemes lexemes = new Lexemes();

		lexemes.add(new Function("Main1", "", "return true;"));
		lexemes.add(new Function("Main2", "", "return true;"));

		Assert.assertEquals("function Main1() {\n\treturn true;\n}\nfunction Main2() {\n\treturn true;\n}\n", lexemes.js(0));
	}
}