package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.word.Comma;
import ru.zainutdinov.vbs2js.word.Else;
import ru.zainutdinov.vbs2js.word.ElseIf;
import ru.zainutdinov.vbs2js.word.EndFunction;
import ru.zainutdinov.vbs2js.word.EndIf;
import ru.zainutdinov.vbs2js.word.EndSub;
import ru.zainutdinov.vbs2js.word.False;
import ru.zainutdinov.vbs2js.word.Function;
import ru.zainutdinov.vbs2js.word.If;
import ru.zainutdinov.vbs2js.word.ParenthesisClose;
import ru.zainutdinov.vbs2js.word.ParenthesisOpen;
import ru.zainutdinov.vbs2js.word.Private;
import ru.zainutdinov.vbs2js.word.Public;
import ru.zainutdinov.vbs2js.word.Return;
import ru.zainutdinov.vbs2js.word.Sub;
import ru.zainutdinov.vbs2js.word.Then;
import ru.zainutdinov.vbs2js.word.True;
import ru.zainutdinov.vbs2js.word.Unknown;

public class WordsTests {

	@Test
	public void testIsEmpty() {
		Assert.assertEquals(false, new Words("(").isEmpty());
		Assert.assertEquals(true, new Words("").isEmpty());
	}

	@Test
	public void testCheckFirst() {
		Words words = new Words("(");

		Assert.assertEquals(false, words.checkFirstClass(ParenthesisClose.class));
		Assert.assertEquals(true, words.checkFirstClass(ParenthesisOpen.class));
	}

	@Test
	public void testCutFirst_unknownTrimSpacesAndNewLines() {
		Words words = new Words("Value1 Value2  Value3\nValue4");

		Assert.assertEquals("Value1", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals("Value2", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals("Value3", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals("Value4", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutFirst_AllWords_CamelCase() {
		Words words = new Words(
				"Public Private Sub End Sub Function End Function If Then ElseIf Else If Else End If (Return) True,False");

		Assert.assertEquals(Public.class, words.cutFirst().getClass());
		Assert.assertEquals(Private.class, words.cutFirst().getClass());
		Assert.assertEquals(Sub.class, words.cutFirst().getClass());
		Assert.assertEquals(EndSub.class, words.cutFirst().getClass());
		Assert.assertEquals(Function.class, words.cutFirst().getClass());
		Assert.assertEquals(EndFunction.class, words.cutFirst().getClass());
		Assert.assertEquals(If.class, words.cutFirst().getClass());
		Assert.assertEquals(Then.class, words.cutFirst().getClass());
		Assert.assertEquals(ElseIf.class, words.cutFirst().getClass());
		Assert.assertEquals(ElseIf.class, words.cutFirst().getClass());
		Assert.assertEquals(Else.class, words.cutFirst().getClass());
		Assert.assertEquals(EndIf.class, words.cutFirst().getClass());
		Assert.assertEquals(ParenthesisOpen.class, words.cutFirst().getClass());
		Assert.assertEquals(Return.class, words.cutFirst().getClass());
		Assert.assertEquals(ParenthesisClose.class, words.cutFirst().getClass());
		Assert.assertEquals(True.class, words.cutFirst().getClass());
		Assert.assertEquals(Comma.class, words.cutFirst().getClass());
		Assert.assertEquals(False.class, words.cutFirst().getClass());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutFirst_AllWords_UpperCase() {
		Words words = new Words(
				"PUBLIC PRIVATE SUB END SUB FUNCTION END FUNCTION IF THEN ELSEIF ELSE IF ELSE END IF (RETURN) TRUE,FALSE");

		Assert.assertEquals(Public.class, words.cutFirst().getClass());
		Assert.assertEquals(Private.class, words.cutFirst().getClass());
		Assert.assertEquals(Sub.class, words.cutFirst().getClass());
		Assert.assertEquals(EndSub.class, words.cutFirst().getClass());
		Assert.assertEquals(Function.class, words.cutFirst().getClass());
		Assert.assertEquals(EndFunction.class, words.cutFirst().getClass());
		Assert.assertEquals(If.class, words.cutFirst().getClass());
		Assert.assertEquals(Then.class, words.cutFirst().getClass());
		Assert.assertEquals(ElseIf.class, words.cutFirst().getClass());
		Assert.assertEquals(ElseIf.class, words.cutFirst().getClass());
		Assert.assertEquals(Else.class, words.cutFirst().getClass());
		Assert.assertEquals(EndIf.class, words.cutFirst().getClass());
		Assert.assertEquals(ParenthesisOpen.class, words.cutFirst().getClass());
		Assert.assertEquals(Return.class, words.cutFirst().getClass());
		Assert.assertEquals(ParenthesisClose.class, words.cutFirst().getClass());
		Assert.assertEquals(True.class, words.cutFirst().getClass());
		Assert.assertEquals(Comma.class, words.cutFirst().getClass());
		Assert.assertEquals(False.class, words.cutFirst().getClass());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutToExclusive() {
		Words words = new Words("Sub SubName End Sub Something");

		Words wordsToSub = words.cutToExclusive(EndSub.class);

		Assert.assertEquals(Sub.class, wordsToSub.cutFirst().getClass());
		Assert.assertEquals("SubName", ((Unknown) wordsToSub.cutFirst()).getText());
		Assert.assertEquals(null, wordsToSub.cutFirst());

		Assert.assertEquals("Something", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testCutToInclusive() {
		Words words = new Words("Sub SubName End Sub Something");

		Words wordsToSub = words.cutToInclusive(EndSub.class);

		Assert.assertEquals(Sub.class, wordsToSub.cutFirst().getClass());
		Assert.assertEquals("SubName", ((Unknown) wordsToSub.cutFirst()).getText());
		Assert.assertEquals(EndSub.class, wordsToSub.cutFirst().getClass());
		Assert.assertEquals(null, wordsToSub.cutFirst());

		Assert.assertEquals("Something", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testClear() {
		Words words = new Words("Something");

		Assert.assertEquals(false, words.isEmpty());

		words.clear();

		Assert.assertEquals(true, words.isEmpty());
	}

	@Test
	public void testAdd() {
		Words words = new Words("Something1");

		words.add(new Unknown("Something2"));

		Assert.assertEquals("Something1", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals("Something2", ((Unknown) words.cutFirst()).getText());
		Assert.assertEquals(null, words.cutFirst());
	}

	@Test
	public void testJS() {
		Words words = new Words("");

		words.add(new Unknown("Something1"));
		words.add(new Unknown("Something2"));

		Assert.assertEquals("Something1Something2", words.js());
	}
}