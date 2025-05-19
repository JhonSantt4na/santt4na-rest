package com.jhonn.santt4na_rest.repository;

import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.model.Person;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Configura o teste para trabalhar com o JPA
@ExtendWith(SpringExtension.class) // Integra o Junit com o Spring
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	PersonRepository repository;
	
	private static Person person;
	
	@BeforeAll
	static void setUp() {
		person = new Person();
	}
	
	@Test
	@Order(1)
	void disablePerson() {
		Pageable pageable = PageRequest.of(
			0,
			12,
			Sort.by(Sort.Direction.ASC, "firstName" ));
		
		person = repository.findPeopleByName("iko", pageable).getContent().get(0);
		
		assertNotNull(person);
		assertNotNull(person.getId());
		assertEquals("Nikola", person.getFirstName());
		assertEquals("Tesla", person.getLastName());
		assertEquals("Male", person.getGender());
		assertEquals("Smiljan - Croatia", person.getAddress());
		assertTrue(person.getEnabled());
	}
	
	@Test
	@Order(2)
	void findPeopleByName() {
		
		Long id = person.getId();
		
		repository.disablePerson(id);
		
		var result = repository.findById(id);
		person = result.get();
		
		assertNotNull(person);
		assertNotNull(person.getId());
		assertEquals("Nikola", person.getFirstName());
		assertEquals("Tesla", person.getLastName());
		assertEquals("Male", person.getGender());
		assertEquals("Smiljan - Croatia", person.getAddress());
		assertFalse(person.getEnabled());
	}
}