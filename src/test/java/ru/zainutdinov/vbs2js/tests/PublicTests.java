package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Private;
import ru.zainutdinov.vbs2js.Public;

public class PublicTests {

	@Test
	public void testJS() {
		Public public_ = new Public();

		Assert.assertEquals("", public_.js());
	}
	
	@Test
	public void testEquals_true() {
		Assert.assertEquals(new Public(), new Public());
	}

	@Test
	public void testEquals_false() {
		Assert.assertNotEquals(new Private(), new Public());
	}
}
