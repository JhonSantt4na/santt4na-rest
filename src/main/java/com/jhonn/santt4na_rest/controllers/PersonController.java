package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.controllers.docs.PersonControllerDocs;
import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Cors a Nivel de Controller
//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {
	
	@Autowired
	private PersonServices service;
	
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
	
	//@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE
		})
	@Override
	public PersonDTO findById(@PathVariable("id") Long id) {
		var person = service.findById(id);
		person.setSensitiveData("Foo Bar");
		return person;
	}
	
	// CORS Para mais de um caminho usamos as ""
	//@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8080"})
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