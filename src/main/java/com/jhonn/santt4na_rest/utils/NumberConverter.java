package com.jhonn.santt4na_rest.utils;

import com.jhonn.santt4na_rest.exceptions.UnsupportedMathOperationException;

public class NumberConverter {
	
	public static Double convertToDouble(String strNumber) throws UnsupportedMathOperationException {
		if (strNumber == null|| strNumber.isEmpty()) {
			throw new UnsupportedMathOperationException("Please set a Numeric Value");
		}
		String number = strNumber.replaceAll(",", ".");
		return Double.parseDouble(number);
	}
}
