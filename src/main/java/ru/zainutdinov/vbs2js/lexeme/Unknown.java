package ru.zainutdinov.vbs2js.lexeme;

import java.util.List;

import ru.zainutdinov.vbs2js.StringUtils;

public class Unknown implements ILexeme {

	private List<ru.zainutdinov.vbs2js.word.Unknown> words;
	
	public Unknown(List<ru.zainutdinov.vbs2js.word.Unknown> words) {
		this.words = words;
	}

	public List<ru.zainutdinov.vbs2js.word.Unknown> getWords() {
		return words;
	}
	
	// TODO: test
	@Override
	public String js(int tabLevel) {
		String result = StringUtils.repeat("\t", tabLevel);
		
		for (ru.zainutdinov.vbs2js.word.Unknown word : words) {
			result += word.getText();
		}

		return result + "\n";
	}

}
