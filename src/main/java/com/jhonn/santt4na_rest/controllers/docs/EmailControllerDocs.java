package com.jhonn.santt4na_rest.controllers.docs;

import com.jhonn.santt4na_rest.dataDTO.v1.request.EmailRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface EmailControllerDocs {
	
	@Operation(summary = "Send an e-Mail",
		description = "Send an e_mail by provider details, subject and body",
		tags = {"Email"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	ResponseEntity<String> sendEmail(EmailRequestDTO emailRequestDTO);
	
	@Operation(summary = "Send an e-Mail with Attachment",
		description = "Send an e_mail with Attachment by provider details, subject and body",
		tags = {"Email"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		})
	ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile);
}
