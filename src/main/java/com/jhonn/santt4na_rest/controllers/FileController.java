package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.controllers.docs.FileControllerDocs;
import com.jhonn.santt4na_rest.dataDTO.v1.UploadFileResponseDTO;
import com.jhonn.santt4na_rest.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService service;
	
	@Override
	@PostMapping("/uploadFile")
	public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
		// Recebemo o file  e passamos para o servico que trata onde vai salvar no disco e retorna o nome do arquivo
		var fileName = service.storeFile(file);
		
		
		// Montando a Uri - Contruindo o caminho de acordo com o ambiente que esta rodando
		//http://localhost:8080/api/file/v1/downloadFile/filename.docx
		var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/file/v1/downloadFile")// Endpoint especifico
			.path(fileName)//
			.toUriString();
		
		
		// Precisamos montar o retorno :
		return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@Override
	@PostMapping("/multipleFiles")
	public List<UploadFileResponseDTO> uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
			.stream()
			.map(this::uploadFile)
			.collect(Collectors.toList());
	}
	
	@Override
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = service.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.error("Could not determine file type!");
		}
		
		if (contentType == null){
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
			.body(resource);
	}
	
}
