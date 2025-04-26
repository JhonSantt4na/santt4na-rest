package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController{
	
	@Autowired
	private PersonServices service;
	
	//GET http://localhost:8080/api/person/v1
	@GetMapping(
		produces = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE})
	public List<PersonDTO> findAll(){
		return service.findAll();
	}
	
	//GET http://localhost:8080/person/1
	@GetMapping(value = "/{id}",
		produces = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE}
	)
	public PersonDTO findById(@PathVariable("id") Long id){
		var person = service.findById(id);
		//person.setBirthDay(new Date());
		//person.setPhoneNumber("+55 (75)8787-4212");
		person.setSensitiveData("Foo Bar");
		return person;
	}
	
	//POST http://localhost:8080/person
	@PostMapping(
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
	)
	public PersonDTO create(@RequestBody PersonDTO person){
		return service.create(person);
	}
	
	//PUT http://localhost:8080/person
	@PutMapping(
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
	)
	public PersonDTO update(@RequestBody PersonDTO person){
		return service.update(person);
	}
	
	//DELETE http://localhost:8080/person/1
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//POST http://localhost:8080/person/v2
	@PostMapping(
		value = "/v2",
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
	)
	public PersonDTOV2 create(@RequestBody PersonDTOV2 person){
		return service.createV2(person);
	}
	
}
