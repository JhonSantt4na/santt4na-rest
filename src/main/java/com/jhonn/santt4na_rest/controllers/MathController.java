package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/math")
public class MathController {
	
	// http://localhost:8080/math/sum/2/3
	@GetMapping("/sum/{number1}/{number2}")
	public Double sum(@PathVariable("number1") String number1,
					  @PathVariable("number2") String number2) {
		if (!isNumeric(number1) || !isNumeric(number2)) {
			throw new IllegalArgumentException();
		}
		
		return convertToDouble(number1) + convertToDouble(number2);
	}
	
	// http://localhost:8080/math/subtraction/3/4
	// http://localhost:8080/math/division/2/5
	// http://localhost:8080/math/multiplication/2/5
	
	private boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		return strNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	private Double convertToDouble(String strNumber) throws UnsupportedMathOperationException {
		if (strNumber == null|| strNumber.isEmpty()) {
			throw new UnsupportedMathOperationException("Please set a Numeric Value");
		}
		String number = strNumber.replaceAll(",", ".");
		return Double.parseDouble(number);
	}
}
