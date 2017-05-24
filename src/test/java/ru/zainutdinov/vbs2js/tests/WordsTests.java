package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;

public class WordsTests {

	@Test
	public void testCutFirst_trimSpaces() {
		Words words = new Words("Sub Main End Sub");

		Assert.assertEquals("Sub", words.cutFirst());
		Assert.assertEquals("Main", words.cutFirst());
		Assert.assertEquals("End", words.cutFirst());
		Assert.assertEquals("Sub", words.cutFirst());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutFirst_trimNewLines() {
		Words words = new Words("Sub Main\nEnd Sub");

		Assert.assertEquals("Sub", words.cutFirst());
		Assert.assertEquals("Main", words.cutFirst());
		Assert.assertEquals("End", words.cutFirst());
		Assert.assertEquals("Sub", words.cutFirst());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutFirst_trimNewLinesAfterParenthesis() {
		Words words = new Words("Sub Main(Parameter)\nEnd Sub");

		Assert.assertEquals("Sub", words.cutFirst());
		Assert.assertEquals("Main", words.cutFirst());
		Assert.assertEquals("(", words.cutFirst());
		Assert.assertEquals("Parameter", words.cutFirst());
		Assert.assertEquals(")", words.cutFirst());
		Assert.assertEquals("End", words.cutFirst());
		Assert.assertEquals("Sub", words.cutFirst());
	}

	@Test
	public void testCutFirst_addSpaceAfterComma() {
		Words words = new Words("Parameter1,Parameter2");

		Assert.assertEquals("Parameter1", words.cutFirst());
		Assert.assertEquals(", ", words.cutFirst());
		Assert.assertEquals("Parameter2", words.cutFirst());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutFirst_addSpacesBeforeAndAfterEqual() {
		Words words = new Words("Parameter1=Parameter2");

		Assert.assertEquals("Parameter1", words.cutFirst());
		Assert.assertEquals(" = ", words.cutFirst());
		Assert.assertEquals("Parameter2", words.cutFirst());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testNextIs() {
		Words words = new Words("Sub");

		Assert.assertEquals(false, words.nextIs("Main"));
		Assert.assertEquals(true, words.nextIs("Sub"));
	}

	@Test
	public void testNextIs_empty() {
		Words words = new Words("");

		Assert.assertEquals(false, words.nextIs("Sub"));
	}
}