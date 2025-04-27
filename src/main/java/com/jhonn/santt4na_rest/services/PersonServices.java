package com.jhonn.santt4na_rest.services;

import com.jhonn.santt4na_rest.controllers.PersonController;
import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.exceptions.ResourceNotFoundException;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObjects;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObject;

import com.jhonn.santt4na_rest.mapper.custon.PersonMapper;
import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class PersonServices {
	
	private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	PersonMapper converter;
	
	private final PersonRepository repository;
	
	public PersonServices(PersonRepository repository) {
		this.repository = repository;
	}
	
	public List<PersonDTO> findAll(){
		logger.info("Finding all Person!");
		var persons = parseObjects(repository.findAll(), PersonDTO.class);
		persons.forEach(PersonServices::addHateoasLinks);
		return persons;
	}
	
	public PersonDTO findById(Long id){
		logger.info("Finding one Person!");
		var entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		var dto = parseObject(entity, PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public PersonDTO create(PersonDTO person) {
		logger.info("Creating one Person!");
		var entity = parseObject(person, Person.class);
		var dto = parseObject(repository.save(entity), PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	// Create V2
	public PersonDTOV2 createV2(PersonDTOV2 person) {
		logger.info("Creating one Person V2!");
		var entity = converter.convertDTOToEntity(person);
		return converter.convertEntityToDTO(repository.save(entity));
	}
	
	public PersonDTO update(PersonDTO person) {
		logger.info("Updating one Person!");
		Person entity = repository.findById(person.getId())
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var dto = parseObject(repository.save(entity), PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public void delete(Long id){
		logger.info("Deleting one Person!");
		Person entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		repository.delete(entity);
	}
	
	
	private static void addHateoasLinks(PersonDTO dto) {
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).findAll()).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).create(dto)).withSelfRel().withType("POST"));
		dto.add(linkTo(methodOn(PersonController.class).update(dto)).withSelfRel().withType("PUT"));
		dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withSelfRel().withType("DELETE"));
	}
	
}

