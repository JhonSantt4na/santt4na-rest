package com.jhonn.santt4na_rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhonn.santt4na_rest.config.EmailConfig;
import com.jhonn.santt4na_rest.dataDTO.v1.request.EmailRequestDTO;
import com.jhonn.santt4na_rest.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class EmailService {
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private EmailConfig emailConfigs;
	
	public void sendSimpleEmail(EmailRequestDTO emailRequest) {
		
		emailSender
			.To(emailRequest.getTo())
			.withSubject(emailRequest.getSubject())
			.withMessage(emailRequest.getBody())
			.send(emailConfigs);
	}
	
	public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
		File tempFile = null;
		try {
			EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
			tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
			attachment.transferTo(tempFile);
			
			emailSender
				.To(emailRequest.getTo())
				.withSubject(emailRequest.getSubject())
				.withMessage(emailRequest.getBody())
				.attachment(tempFile.getAbsolutePath())
				.send(emailConfigs);
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error parsing email request JSON",e);
		} catch (IOException e) {
			throw new RuntimeException("Error Processing the attachment",e);
		}finally {
			if (tempFile != null && tempFile.exists()){
				tempFile.delete();
			}
		}
	
	}
}
