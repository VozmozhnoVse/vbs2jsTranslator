package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Lexemes;
import ru.zainutdinov.vbs2js.Private;
import ru.zainutdinov.vbs2js.Public;
import ru.zainutdinov.vbs2js.Sub;
import ru.zainutdinov.vbs2js.VBS;

public class VBSTests {

	@Test
	public void testLexemes_SimpleSub() {
		VBS vbs = new VBS("Sub Main\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();
		
		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(0));
	}

	@Test
	public void testLexemes_TwoSimpleSub() {
		VBS vbs = new VBS("Sub Main1\nEnd Sub\nSub Main2\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Sub("Main1", "", ""), lexemes.get(0));
		Assert.assertEquals(new Sub("Main2", "", ""), lexemes.get(1));
	}

	@Test
	public void testLexemes_SimpleSubWithPublic() {
		VBS vbs = new VBS("Public Sub Main\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Public(), lexemes.get(0));
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(1));
	}

	@Test
	public void testLexemes_SimpleSubWithPrivate() {
		VBS vbs = new VBS("Private Sub Main\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(2, lexemes.size());
		Assert.assertEquals(new Private(), lexemes.get(0));
		Assert.assertEquals(new Sub("Main", "", ""), lexemes.get(1));
	}

	@Test
	public void testLexemes_SimpleSubWithParameters() {
		VBS vbs = new VBS("Sub Main(Parameter1, Parameter2)\nEnd Sub");

		Lexemes lexemes = vbs.lexemes();

		Assert.assertEquals(1, lexemes.size());
		Assert.assertEquals(new Sub("Main", "Parameter1, Parameter2", ""), lexemes.get(0));
	}

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
}