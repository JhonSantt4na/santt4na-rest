package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.dataDTO.v1.security.AccountCredentialsDTO;
import com.jhonn.santt4na_rest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService service;
	
	@Operation(summary = "Authenticates an user and returns a token")
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
		if (credentialsIsInvalid(credentials)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client request!");
		}
		var token = service.signIn(credentials);
		
		if (token == null) {
			return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		return ResponseEntity.ok().body(token);
	}
	
	
	private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
		return credentials == null ||
			StringUtils.isBlank(credentials.getPassword()) ||
			StringUtils.isBlank(credentials.getUsername());
	}
	
}
