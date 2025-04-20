package com.jhonn.santt4na_rest.controllers;

import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController{
	
	@Autowired
	private PersonServices service = new PersonServices();
	
	//GET http://localhost:8080/person
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<Person> findAll(){
		
		return service.findAll();
		
	}
	
	//GET http://localhost:8080/person/1
	@RequestMapping(value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Person findById(@PathVariable("id") String id){
		return service.findById(id);
	}
	
	
	//POST http://localhost:8080/person
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Person create(@RequestBody Person person){
		return service.create(person);
	}
	
	//PUT http://localhost:8080/person
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Person update(@RequestBody Person person){
		return service.update(person);
	}
	
	//DELETE http://localhost:8080/person
	@RequestMapping(value = "/{id}")
	public void delete(@PathVariable("id") String id){
		service.delete(id);
	}
	
}
