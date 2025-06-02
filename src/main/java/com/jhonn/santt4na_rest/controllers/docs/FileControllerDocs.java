package com.jhonn.santt4na_rest.controllers.docs;

import com.jhonn.santt4na_rest.dataDTO.v1.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {

	UploadFileResponseDTO uploadFile (MultipartFile file);
	List<UploadFileResponseDTO> uploadMultipleFile (MultipartFile[] files);
	ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
