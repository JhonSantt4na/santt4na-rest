package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.controllers.docs.AuthControllerDocs;
import com.jhonn.santt4na_rest.dataDTO.v1.security.AccountCredentialsDTO;
import com.jhonn.santt4na_rest.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public abstract class AuthController implements AuthControllerDocs {
	
	@Autowired
	AuthService service;
	
	@PostMapping("/signin")
	@Override
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
		if (credentialsIsInvalid(credentials)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client request!");
		}
		var token = service.signIn(credentials);
		
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		return ResponseEntity.ok().body(token);
	}
	
	@PutMapping("/refresh/{username}")
	@Override
	public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
									 @RequestHeader("Authorization") String refreshToken) {

		if (parametersAreInvalid(username, refreshToken)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client request!");
		}
		var token = service.refreshToken(username, refreshToken);
		
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		return ResponseEntity.ok().body(token);
	}
	
	private boolean parametersAreInvalid(String username, String refreshToken) {
		return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
	}
	
	private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
		return credentials == null ||
			StringUtils.isBlank(credentials.getPassword()) ||
			StringUtils.isBlank(credentials.getUsername());
	}
	
	@PostMapping(
		value = "/createUser",
		consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE
		},
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE
		}
	)
	@Override
	public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials) {
		return service.create(credentials);
	}
	
}