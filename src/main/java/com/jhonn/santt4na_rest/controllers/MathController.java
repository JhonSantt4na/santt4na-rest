package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.math.SimpleMath;
import com.jhonn.santt4na_rest.utils.NumberConverter;
import com.jhonn.santt4na_rest.utils.VerifyNumber;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math")
public class MathController {
	
	private final SimpleMath math = new SimpleMath();
	
	// http://localhost:8080/math/sum/2/3
	@GetMapping("/sum/{number1}/{number2}")
	public Double sum(@PathVariable("number1") String number1,
					  @PathVariable("number2") String number2) {
		if (!VerifyNumber.isNumeric(number1) || !VerifyNumber.isNumeric(number2)) {
			throw new IllegalArgumentException();
		}
		
		return math.sum(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
	}
	
	// http://localhost:8080/math/subtraction/3/4
	@GetMapping("/subtraction/{number1}/{number2}")
	public Double subtraction( @PathVariable("number1") String number1,
							   @PathVariable("number2") String number2){
		if (!VerifyNumber.isNumeric(number1) || !VerifyNumber.isNumeric(number2)) {
			throw new IllegalArgumentException();
		}
		
		return math.subtraction(NumberConverter.convertToDouble(number1),NumberConverter.convertToDouble(number2));
	}
	
	// http://localhost:8080/math/multiplication/3/4
	@GetMapping("/multiplication/{number1}/{number2}")
	public Double multiplication( @PathVariable("number1") String number1,
							   @PathVariable("number2") String number2){
		if (!VerifyNumber.isNumeric(number1) || !VerifyNumber.isNumeric(number2)) {
			throw new IllegalArgumentException();
		}
		
		return math.multiplication(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
	}
	
	// http://localhost:8080/math/division/2/5
	@GetMapping("/division/{number1}/{number2}")
	public Double division( @PathVariable("number1") String number1,
								  @PathVariable("number2") String number2){
		if (!VerifyNumber.isNumeric(number1) || !VerifyNumber.isNumeric(number2)) {
			throw new IllegalArgumentException();
		}
		
		return math.division(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
	}
	
	// http://localhost:8080/math/mean/2/5
	@GetMapping("/mean/{number1}/{number2}")
	public Double mean( @PathVariable("number1") String number1,
							@PathVariable("number2") String number2){
		if (!VerifyNumber.isNumeric(number1) || !VerifyNumber.isNumeric(number2)) {
			throw new IllegalArgumentException();
		}
		
		return math.mean(NumberConverter.convertToDouble(number1), NumberConverter.convertToDouble(number2));
	}
	
	// http://localhost:8080/math/squareroot/2
	@GetMapping("/squareRoot/{number}")
	public Double squareRoot(@PathVariable("number") String number){
		if (!VerifyNumber.isNumeric(number)) {
			throw new IllegalArgumentException();
		}
		return math.squareRoot(NumberConverter.convertToDouble(number));
	}
}