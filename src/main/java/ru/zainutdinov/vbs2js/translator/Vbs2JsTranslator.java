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

	public String translateFunction(String vbsCode) {
		return vbsCode;
	}

	public String translateIfStatement(String vbsCode) {
		String result = vbsCode;

		result = vbsCode
				.replace("Then", "{")
				.replace("ElseIf", "} else if")
				.replace("Else", "} else {")
				.replace("End If", "}")
				.toLowerCase();

		return result;
	}
}
