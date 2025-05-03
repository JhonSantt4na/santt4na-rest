package com.jhonn.santt4na_rest.unitetests.services;


import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.exceptions.RequiredObjectIsNullException;
import com.jhonn.santt4na_rest.unitetests.mapper.mocks.MockPerson;
import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
	
	private final MockPerson input = new MockPerson();
	
	@Mock
	private PersonRepository repository;
	
	@InjectMocks
	private PersonServices service;
	
	@Test
	void findById() {
		
		Person person = input.mockEntity(1);
		person.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(person));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void create() {
		Person person = input.mockEntity(1);
		Person persisted = person;
		persisted.setId(1L);
		if (person == null) {
			throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
		}
		PersonDTO dto = input.mockDTO(1);
		
		when(repository.save(person)).thenReturn(persisted);
		
		var result = service.create(dto);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testCreate_WithNullPerson_ShouldThrowRequiredObjectIsNullException() {
		RequiredObjectIsNullException exception = assertThrows(
			RequiredObjectIsNullException.class,
			() -> service.create(null)
		);
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage),
			"A mensagem da exceção deveria conter: " + expectedMessage);
	}
	
	@Test
	void update() {
		Person person = input.mockEntity(1);
		Person persisted = person;
		persisted.setId(1L);
		
		PersonDTO dto = input.mockDTO(1);
		
		when(repository.findById(1L)).thenReturn(Optional.of(person));
		when(repository.save(person)).thenReturn(persisted);
		
		var result = service.update(dto);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testUpdate_WithNullPerson_ShouldThrowRequiredObjectIsNullException() {
		RequiredObjectIsNullException exception = assertThrows(
			RequiredObjectIsNullException.class,
			() -> service.update(null)
		);
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage),
			"A mensagem da exceção deveria conter: " + expectedMessage);
	}
	
	@Test
	void delete() {
		Person person = input.mockEntity(1);
		person.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(person));
		
		service.delete(1L);
		verify(repository, times(1)).findById(anyLong());
		verify(repository, times(1)).delete(any(Person.class));
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void findAll() {
		List<Person> list = input.mockEntityList();
		when(repository.findAll()).thenReturn(list);
		List<PersonDTO> people = service.findAll();
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		var personOne = people.get(1);
		
		assertNotNull(personOne);
		assertNotNull(personOne.getId());
		assertNotNull(personOne.getLinks());
		
		assertNotNull(personOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(personOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(personOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(personOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(personOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test1", personOne.getAddress());
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("Female", personOne.getGender());
		
		var personFour = people.get(4);
		
		assertNotNull(personFour);
		assertNotNull(personFour.getId());
		assertNotNull(personFour.getLinks());
		
		assertNotNull(personFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/4")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(personFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(personFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(personFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(personFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/4")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test4", personFour.getAddress());
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("Male", personFour.getGender());
		
		var personSeven = people.get(7);
		
		assertNotNull(personSeven);
		assertNotNull(personSeven.getId());
		assertNotNull(personSeven.getLinks());
		
		assertNotNull(personSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/7")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(personSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(personSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(personSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(personSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/7")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test7", personSeven.getAddress());
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("Female", personSeven.getGender());
	}
}