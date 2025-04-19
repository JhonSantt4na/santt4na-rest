package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
	
	private static final String template = "Helo, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	// http://localhost:8080/greeting?name=Jhonn
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "Word") String name){
		return  new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
}
