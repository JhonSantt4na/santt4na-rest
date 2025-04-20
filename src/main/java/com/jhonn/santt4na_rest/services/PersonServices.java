package com.jhonn.santt4na_rest.services;

import com.jhonn.santt4na_rest.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private final Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public Person findById(String id){
		logger.info("Finding one Person!");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("firstName_133");
		person.setLastName("lastName_0cb");
		person.setAddress("address_22e4bb9890e7");
		person.setGeder("male");
		return person;
	}
	
	public List<Person> findAll(){
		logger.info("Finding all Person!");
		List<Person> persons = new ArrayList<Person>();
		
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		
		return persons;
	}
	
	public Person create(Person person) {
		logger.info("Creating one Person!");
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Updating one Person!");
		return person;
	}
	
	public void delete(String id){
		logger.info("Deleting one Person!");
		
	}
	
	private Person mockPerson(int i) {
		Person mockPerson = new Person();
		mockPerson.setId(counter.incrementAndGet());
		mockPerson.setFirstName("FirstName " + i);
		mockPerson.setLastName("LastName " + i);
		mockPerson.setAddress("Some Address in Brasil");
		mockPerson.setGeder("Male");
		return mockPerson;
	}
	
	
}
	


