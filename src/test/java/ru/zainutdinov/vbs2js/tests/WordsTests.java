package ru.zainutdinov.vbs2js.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.word.Sub;
import ru.zainutdinov.vbs2js.word.Then;
import ru.zainutdinov.vbs2js.word.True;
import ru.zainutdinov.vbs2js.word.Unknown;
import ru.zainutdinov.vbs2js.word.Public;
import ru.zainutdinov.vbs2js.word.Return;
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

public class WordsTests {

	@Test
	public void testCutFirst_unknownTrimSpacesAndNewLines() {
		Words words = new Words("Value1 Value2  Value3\nValue4");

		Assert.assertEquals(4, words.getWords().size());
		Assert.assertEquals("Value1", ((Unknown)words.getWords().get(0)).getText());
		Assert.assertEquals("Value2", ((Unknown)words.getWords().get(1)).getText());
		Assert.assertEquals("Value3", ((Unknown)words.getWords().get(2)).getText());
		Assert.assertEquals("Value4", ((Unknown)words.getWords().get(3)).getText());
	}

	@Test
	public void testCutFirst_AllWords_CamelCase() {
		Words words = new Words("Public Private Sub End Sub Function End Function If Then ElseIf Else If Else End If (Return) True,False");

		Assert.assertEquals(18, words.getWords().size());
		Assert.assertEquals(Public.class, 			words.getWords().get(0).getClass());
		Assert.assertEquals(Private.class, 			words.getWords().get(1).getClass());
		Assert.assertEquals(Sub.class, 				words.getWords().get(2).getClass());
		Assert.assertEquals(EndSub.class, 			words.getWords().get(3).getClass());
		Assert.assertEquals(Function.class, 		words.getWords().get(4).getClass());
		Assert.assertEquals(EndFunction.class, 		words.getWords().get(5).getClass());
		Assert.assertEquals(If.class, 				words.getWords().get(6).getClass());
		Assert.assertEquals(Then.class, 			words.getWords().get(7).getClass());
		Assert.assertEquals(ElseIf.class, 			words.getWords().get(8).getClass());
		Assert.assertEquals(ElseIf.class, 			words.getWords().get(9).getClass());
		Assert.assertEquals(Else.class, 			words.getWords().get(10).getClass());
		Assert.assertEquals(EndIf.class, 			words.getWords().get(11).getClass());
		Assert.assertEquals(ParenthesisOpen.class, 	words.getWords().get(12).getClass());
		Assert.assertEquals(Return.class, 			words.getWords().get(13).getClass());
		Assert.assertEquals(ParenthesisClose.class, words.getWords().get(14).getClass());
		Assert.assertEquals(True.class, 			words.getWords().get(15).getClass());
		Assert.assertEquals(Comma.class, 			words.getWords().get(16).getClass());
		Assert.assertEquals(False.class, 			words.getWords().get(17).getClass());
	}

	@Test
	public void testCutFirst_AllWords_UpperCase() {
		Words words = new Words("PUBLIC PRIVATE SUB END SUB FUNCTION END FUNCTION IF THEN ELSEIF ELSE IF ELSE END IF (RETURN) TRUE,FALSE");

		Assert.assertEquals(18, words.getWords().size());
		Assert.assertEquals(Public.class, 			words.getWords().get(0).getClass());
		Assert.assertEquals(Private.class, 			words.getWords().get(1).getClass());
		Assert.assertEquals(Sub.class, 				words.getWords().get(2).getClass());
		Assert.assertEquals(EndSub.class, 			words.getWords().get(3).getClass());
		Assert.assertEquals(Function.class, 		words.getWords().get(4).getClass());
		Assert.assertEquals(EndFunction.class, 		words.getWords().get(5).getClass());
		Assert.assertEquals(If.class, 				words.getWords().get(6).getClass());
		Assert.assertEquals(Then.class, 			words.getWords().get(7).getClass());
		Assert.assertEquals(ElseIf.class, 			words.getWords().get(8).getClass());
		Assert.assertEquals(ElseIf.class, 			words.getWords().get(9).getClass());
		Assert.assertEquals(Else.class, 			words.getWords().get(10).getClass());
		Assert.assertEquals(EndIf.class, 			words.getWords().get(11).getClass());
		Assert.assertEquals(ParenthesisOpen.class, 	words.getWords().get(12).getClass());
		Assert.assertEquals(Return.class, 			words.getWords().get(13).getClass());
		Assert.assertEquals(ParenthesisClose.class, words.getWords().get(14).getClass());
		Assert.assertEquals(True.class, 			words.getWords().get(15).getClass());
		Assert.assertEquals(Comma.class, 			words.getWords().get(16).getClass());
		Assert.assertEquals(False.class, 			words.getWords().get(17).getClass());
	}
}
