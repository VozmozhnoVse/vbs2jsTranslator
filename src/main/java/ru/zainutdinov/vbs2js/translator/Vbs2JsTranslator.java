package ru.zainutdinov.vbs2js.translator;

public class Vbs2JsTranslator {
	public Vbs2JsTranslator() {
	}

	/* TODO: delete


	/* TODO: delete
	private String replaceFunction(String vbsCode) {
		String result = vbsCode;
		int functionPosition = result.indexOf("Function");

		if (functionPosition >= 0) {
			int parametersPosition = result.indexOf("(", functionPosition + 3);
			int spacePosition = result.indexOf("\n", functionPosition + 3);

			String functionName = result.substring(functionPosition + 9, spacePosition);

			if (parametersPosition > 0 && parametersPosition < spacePosition) {
				result = result.substring(0, functionPosition) + "function " + functionName + " {" + result.substring(spacePosition, result.length());
				functionName = functionName.substring(0, functionName.indexOf("("));
			}
			else {
				result = result.substring(0, functionPosition) + "function " + functionName + "() {" + result.substring(spacePosition, result.length());
			}
			
			result = replaceReturn(functionName, result);
			
			result = replaceFunction(result);
		}

		return result;
	}
	*/

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

	public String translateFor(String vbsCode) {
		return vbsCode;
	}

	public String translateTrue(String vbsCode) {
		return vbsCode;
	}

	public String translateFalse(String vbsCode) {
		return vbsCode;
	}
}
