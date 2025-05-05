package com.jhonn.santt4na_rest.services;

import com.jhonn.santt4na_rest.controllers.PersonController;
import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.exceptions.RequiredObjectIsNullException;
import com.jhonn.santt4na_rest.exceptions.ResourceNotFoundException;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObjects;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObject;

import com.jhonn.santt4na_rest.mapper.custon.PersonMapper;
import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServices {
	
	private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
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
		if (person == null) {
			throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
		}
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
		if (person == null) {
			throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
		}
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
	
	@Transactional
	public PersonDTO disablePerson(Long id){
		logger.info("Disabling one Person!");
		
		repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		repository.disablePerson(id);
		var entity = repository.findById(id).get();
		
		var dto = parseObject(entity, PersonDTO.class);
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
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withRel("findById").withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
		dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
		dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
		dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
	}
	
}

