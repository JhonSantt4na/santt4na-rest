package com.jhonn.santt4na_rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLogController {
	
	private final Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());
	
	@GetMapping("/api/test/v1")
	public String testLog(){
		logger.debug("This is an DEBUG Log");
		logger.info("This is an INFO Log");
		logger.warn("This is an WARN Log");
		logger.error("This is an ERROR Log");
		return "Logs Generated successfuly";
	}

}
