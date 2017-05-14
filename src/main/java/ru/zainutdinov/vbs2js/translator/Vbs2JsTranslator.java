package ru.zainutdinov.vbs2js.translator;

public class Vbs2JsTranslator {
	public Vbs2JsTranslator() {
	}

	private String replaceSub(String vbsCode) {
		String result = vbsCode;
		int subPosition = result.indexOf("Sub");
		
		if (subPosition >= 0) {
			int parametersPosition = result.indexOf("(", subPosition + 3);
			int spacePosition = result.indexOf("\n", subPosition + 3);
			
			if (parametersPosition > 0 && parametersPosition < spacePosition) {
				result = result.substring(0, spacePosition) + " {" + result.substring(spacePosition, result.length());
			}
			else {
				result = result.substring(0, spacePosition) + "() {" + result.substring(spacePosition, result.length());
			}

			result = result.replaceFirst("Sub", "function");
			result = replaceSub(result);
		}

		return result;
	}

	public String translateSub(String vbsCode) {
		String result = vbsCode;
		
		result = result.replaceAll("((Public)|(Private))\\s", "");
		result = result.replace("End Sub", "}");
		result = replaceSub(result);
		
		return result;
	}

	public String translateIfStatement(String vbsCode) {
		return "";
	}
}
