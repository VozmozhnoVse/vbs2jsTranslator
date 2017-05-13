package ru.zainutdinov.vbs2js.translator;

public class Vbs2JsTranslator {
	public Vbs2JsTranslator() {
	}

	public String translate(String vbsCode) {
		String result = vbsCode.replaceAll("Public | Private", "");

		String[] blocks = result.split("End Sub");

		return result;
	}
}
