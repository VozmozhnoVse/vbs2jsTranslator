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

	private String replaceReturn(String functionName, String vbsCode) {
		String result = vbsCode;

		int returnPosition = result.indexOf(functionName + " =");

		if (returnPosition >= 0) {
			int endPosition = result.indexOf("\n", returnPosition + functionName.length() + 3);

			result = result.substring(0, endPosition) + ";" + result.substring(endPosition, result.length());
			result = result.substring(0, returnPosition) + "return" + result.substring( + functionName.length() + 2 + returnPosition, result.length());

			result = replaceReturn(functionName, result);
		}

		return result;
	}

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

	public String translateSub(String vbsCode) {
		String result = vbsCode;
		
		result = result.replaceAll("((Public)|(Private))\\s", "");
		result = result.replace("End Sub", "}");
		result = replaceSub(result);
		
		return result;
	}

	public String translateFunction(String vbsCode) {
		String result = vbsCode;
		
		result = result.replaceAll("((Public)|(Private))\\s", "");
		result = result.replace("End Function", "}");
		result = replaceFunction(result);

		return result;
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
