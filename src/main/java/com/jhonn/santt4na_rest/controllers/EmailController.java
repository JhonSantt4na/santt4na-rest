package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.controllers.docs.EmailControllerDocs;
import com.jhonn.santt4na_rest.dataDTO.v1.request.EmailRequestDTO;
import com.jhonn.santt4na_rest.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {
	
	@Autowired
	private EmailService service;
	
	
	@PostMapping()
	@Override
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequest) {
		service.sendSimpleEmail(emailRequest);
		return new ResponseEntity<>("E_mail send with success", HttpStatus.OK);
	}
	
	@PostMapping(value = "/withAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Override
	public ResponseEntity<String> sendEmailWithAttachment(
		@RequestParam("emailRequest")  String emailRequest,
		@RequestParam("attachment") MultipartFile attachment) {
		
		service.sendEmailWithAttachment(emailRequest, attachment);
		return new ResponseEntity<>("Email with attachment send succesfull", HttpStatus.OK);
	}
}
