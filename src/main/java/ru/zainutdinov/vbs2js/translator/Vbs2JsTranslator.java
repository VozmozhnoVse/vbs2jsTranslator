package ru.zainutdinov.vbs2js.translator;

public class Vbs2JsTranslator {
	public Vbs2JsTranslator() {
	}

	private String replaceSub(String vbsCode) {
		String result = vbsCode;
		int subPosition = result.indexOf("Sub");
		
		if (subPosition >= 0) {
			int spacePosition = result.indexOf("\n", subPosition + 3);
			
			result = result.substring(0, spacePosition) + "() {" + result.substring(spacePosition, result.length());
			result = result.replace("Sub", "function");
			
			result = replaceSub(result);
		}

		return result;
	}
	
	public String translate(String vbsCode) {
		String result = vbsCode;
		
		result = result.replace("Public ", "");
		result = result.replace("End Sub", "}");
		result = replaceSub(result);
		
		return result;
	}
}
