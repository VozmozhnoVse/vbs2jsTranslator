package ru.zainutdinov.vbs2js.lexeme.tests;

import org.junit.Assert;
import org.junit.Test;

import ru.zainutdinov.vbs2js.Words;
import ru.zainutdinov.vbs2js.lexeme.If;

public class IfTests {

	@Test
	public void testJS_If() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("something"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		If if_ = new If(words);

		Assert.assertEquals("if (Expression) {\n\tsomething\n}\n", if_.js(0));
	}

	@Test
	public void testJS_IfElse() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("something1"));
		words.add(new ru.zainutdinov.vbs2js.word.Else());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("something2"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		If if_ = new If(words);

		Assert.assertEquals("if (Expression) {\n\tsomething1\n}\nelse {\n\tsomething2\n}\n", if_.js(0));
	}

	@Test
	public void testJS_IfElseIfElse() {
		Words words = new Words("");
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression1"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("something1"));
		words.add(new ru.zainutdinov.vbs2js.word.ElseIf());
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisOpen());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("Expression2"));
		words.add(new ru.zainutdinov.vbs2js.word.ParenthesisClose());
		words.add(new ru.zainutdinov.vbs2js.word.Then());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("something2"));
		words.add(new ru.zainutdinov.vbs2js.word.Else());
		words.add(new ru.zainutdinov.vbs2js.word.Unknown("something3"));
		words.add(new ru.zainutdinov.vbs2js.word.EndIf());

		If if_ = new If(words);

		Assert.assertEquals(
				"if (Expression1) {\n\tsomething1\n}\nelse if (Expression2) {\n\tsomething2\n}\nelse {\n\tsomething3\n}\n",
				if_.js(0));
		Assert.assertEquals(
				"\tif (Expression1) {\n\t\tsomething1\n\t}\n\telse if (Expression2) {\n\t\tsomething2\n\t}\n\telse {\n\t\tsomething3\n\t}\n",
				if_.js(1));
	}
}