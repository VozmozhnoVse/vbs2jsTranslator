package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.lexeme.Private;
import ru.zainutdinov.vbs2js.lexeme.Public;

public class PrivateTests {

	@Test
	public void testJS() {
		Private private_ = new Private();

		Assert.assertEquals("", private_.js(0));
	}

	@Test
	public void testEquals_true() {
		Assert.assertEquals(new Private(), new Private());
	}

	@Test
	public void testEquals_false() {
		Assert.assertNotEquals(new Public(), new Private());
	}

}
