package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.dataDTO.PersonDTO;
import com.jhonn.santt4na_rest.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController{
	
	@Autowired
	private PersonServices service;
	
	//GET http://localhost:8080/person
	@GetMapping(
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<PersonDTO> findAll(){
		return service.findAll();
	}
	
	//GET http://localhost:8080/person/1
	@GetMapping(value = "/{id}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public PersonDTO findById(@PathVariable("id") Long id){
		return service.findById(id);
	}
	
	//POST http://localhost:8080/person
	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public PersonDTO create(@RequestBody PersonDTO person){
		return service.create(person);
	}
	
	//PUT http://localhost:8080/person
	@PutMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
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
	
}
