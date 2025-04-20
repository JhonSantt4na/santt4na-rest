package com.jhonn.santt4na_rest.services;

import com.jhonn.santt4na_rest.exceptions.ResourceNotFoundException;
import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
	
	private final Logger logger = Logger.getLogger(PersonServices.class.getName());
	private final AtomicLong counter = new AtomicLong();
	
	private final PersonRepository repository;
	
	public PersonServices(PersonRepository repository) {
		this.repository = repository;
	}
	
	public List<Person> findAll(){
		logger.info("Finding all Person!");
		return repository.findAll();
	}
	
	public Person findById(Long id){
		logger.info("Finding one Person!");
		return repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
	}
	
	public Person create(Person person) {
		logger.info("Creating one Person!");
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Updating one Person!");
		Person entity = repository.findById(person.getId())
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(entity);
	}
	
	public void delete(Long id){
		logger.info("Deleting one Person!");
		Person entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		repository.delete(entity);
	}
	
}
	


