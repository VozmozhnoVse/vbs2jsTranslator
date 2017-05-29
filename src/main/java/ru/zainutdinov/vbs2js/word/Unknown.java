package ru.zainutdinov.vbs2js.word;

public class Unknown implements IWord {

	private String text;

	public Unknown(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String js() {
		// TODO Auto-generated method stub
		return null;
	}
}
