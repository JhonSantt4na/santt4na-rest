package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.controllers.docs.PersonControllerDocs;
import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public List<PersonDTO> findAll() {
		return service.findAll();
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
		person.setSensitiveData("Foo Bar");
		return person;
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