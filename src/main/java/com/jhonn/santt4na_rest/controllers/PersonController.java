package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.controllers.docs.PersonControllerDocs;
import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.file.exporter.MediaTypes;
import com.jhonn.santt4na_rest.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {
	
	@Autowired
	private PersonService service;
	
	@GetMapping(produces = {
		MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE,
		MediaType.APPLICATION_YAML_VALUE
	})
	@Override
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "12") Integer size,
		@RequestParam(value = "direction", defaultValue = "asc") String direction
	){
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, sortDirection, "firstName");
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(value = "/exportPage", produces = {
		MediaTypes.APPLICATION_XLSX_VALUE,
		MediaTypes.APPLICATION_PDF_VALUE,
		MediaTypes.APPLICATION_CSV_VALUE
	})
	@Override
	public ResponseEntity<Resource> exportPage(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "12") Integer size,
		@RequestParam(value = "direction", defaultValue = "asc") String direction,
		HttpServletRequest request
	) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		
		String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
		
		Resource file = service.exportPage(pageable, acceptHeader);
		
		Map<String, String> extensionMap = Map.of(
			MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
			MediaTypes.APPLICATION_CSV_VALUE, ".csv",
			MediaTypes.APPLICATION_PDF_VALUE, ".pdf"
		);
		
		var fileExtension = extensionMap.getOrDefault(acceptHeader, "");
		var contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";
		
		var filename = "people_exported" + fileExtension;
		
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(contentType))
			.header(
				HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"")
			.body(file);
	}
		
	@GetMapping(value = "/findPeopleByName/{firstName}", produces = {
		MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE,
		MediaType.APPLICATION_YAML_VALUE
	})
	@Override
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
		@PathVariable("firstName") String firstName,
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "12") Integer size,
		@RequestParam(value = "direction", defaultValue = "asc") String direction
	){
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, sortDirection, "firstName");
		return ResponseEntity.ok(service.findByName(firstName, pageable));
	}
	
	@GetMapping(value = "/{id}",
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE
		})
	@Override
	public PersonDTO findById(@PathVariable("id") Long id) {
		var person = service.findById(id);
		return person;
	}
	
	@GetMapping(value = "/export/{id}",
		produces = {
			MediaType.APPLICATION_PDF_VALUE}
	)
	@Override
	public ResponseEntity<Resource> export(@PathVariable("id") Long id, HttpServletRequest request) {
		
		String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
		Resource file = service.exportPerson(id, acceptHeader);
		
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(acceptHeader))
			.header(
				HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=person.pdf")
			.body(file);
	}
	
	@PostMapping(
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
	public PersonDTO create(@RequestBody PersonDTO person) {
		return service.create(person);
	}
	
	@PostMapping(value = "/massCreation",
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE
		}
	)
	@Override
	public List<PersonDTO> massCreation(@RequestParam("file") MultipartFile file) {
		return service.massCreation(file);
	}
	
	@PutMapping(
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
	public PersonDTO update(@RequestBody PersonDTO person) {
		return service.update(person);
	}
	
	@PatchMapping(value = "/{id}",
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE}
	)
	@Override
	public PersonDTO disablePerson(@PathVariable("id") Long id) {
		return service.disablePerson(id);
	}
	
	@DeleteMapping(value = "/{id}")
	@Override
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(
		value = "/v2",
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
	public PersonDTOV2 create(@RequestBody PersonDTOV2 person) {
		return service.createV2(person);
	}
}