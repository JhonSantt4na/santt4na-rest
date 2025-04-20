package com.jhonn.santt4na_rest.utils;

public class VerifyNumber {
	public static boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		return strNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
