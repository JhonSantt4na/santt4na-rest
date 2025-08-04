package com.jhonn.santt4na_rest.security;

import com.jhonn.santt4na_rest.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
	
	@Autowired
	private final JwtTokenProvider tokenProvider;
	
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
		throws IOException, ServletException {
		var token = tokenProvider.resolveToken((HttpServletRequest) request); // get Token
	
		if (StringUtils.isNotBlank(token) && tokenProvider.validateToken(token)){
			
			Authentication authentication = tokenProvider.getAuthentication(token);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filter.doFilter(request, response); // Passes the request to the next filter or if you do not have a pass for the application
	}
}
