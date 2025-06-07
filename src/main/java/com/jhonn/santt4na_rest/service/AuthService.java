package com.jhonn.santt4na_rest.service;

import com.jhonn.santt4na_rest.dataDTO.v1.security.AccountCredentialsDTO;
import com.jhonn.santt4na_rest.dataDTO.v1.security.TokenDTO;
import com.jhonn.santt4na_rest.repository.UserRepository;
import com.jhonn.santt4na_rest.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials) {
		
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				credentials.getUsername(),
				credentials.getPassword()
			)
		);
		
		var user = repository.findByUsername(credentials.getUsername());
		
		if (user == null) {
			throw  new UsernameNotFoundException("Username " + credentials.getUsername() + " not found.");
		}
		
		var token = tokenProvider.createAccessToken(
			credentials.getUsername(),
			user.getRoles()
		);
		
		return ResponseEntity.ok(token);
	}
}
