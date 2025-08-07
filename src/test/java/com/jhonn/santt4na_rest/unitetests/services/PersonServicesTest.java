package com.jhonn.santt4na_rest.unitetests.services;


import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.exceptions.RequiredObjectIsNullException;
import com.jhonn.santt4na_rest.service.PersonService;
import com.jhonn.santt4na_rest.unitetests.mapper.mocks.MockPerson;
import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.repository.PersonRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
	
	private final MockPerson input = new MockPerson();
	
	@Mock
	private PersonRepository repository;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PagedResourcesAssembler<PersonDTO> assembler;
	
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
		
		// Mocking repository access
		List<Person> mockEntityList = input.mockEntityList();
		Page<Person> mockPage = new PageImpl<>(mockEntityList);
		when(repository.findAll(any(Pageable.class))).thenReturn(mockPage);
		
		List<PersonDTO> mockDtoList = input.mockDTOList();
		
		List<EntityModel<PersonDTO>> entityModels = mockDtoList.stream()
			.map(EntityModel::of)
			.collect(Collectors.toList());
		
		PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
			mockPage.getSize(),
			mockPage.getNumber(),
			mockPage.getTotalElements(),
			mockPage.getTotalPages()
		);
		
		PagedModel<EntityModel<PersonDTO>> mockPagedModel = PagedModel.of(entityModels, pageMetadata);
		when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(mockPagedModel);
		
		PagedModel<EntityModel<PersonDTO>> result = service.findAll(PageRequest.of(0, 14));
		
		List<PersonDTO> people = result.getContent()
			.stream()
			.map(EntityModel::getContent)
			.collect(Collectors.toList());
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		validateIndividualPerson(people.get(1), 1);
		validateIndividualPerson(people.get(4), 4);
		validateIndividualPerson(people.get(7), 7);
	}
	
	private static void validateIndividualPerson(PersonDTO person, int i) {
		assertNotNull(person);
		assertNotNull(person.getId());
		assertNotNull(person.getLinks());
		
		assertNotNull(person.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/person/v1/" + i)
				&& link.getType().equals("GET")
			));
		
		assertNotNull(person.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(person.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(person.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/person/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(person.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/person/v1/" + i)
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Address Test" + i, person.getAddress());
		assertEquals("First Name Test" + i, person.getFirstName());
		assertEquals("Last Name Test" + i, person.getLastName());
		assertEquals(((i % 2)==0) ? "Male" : "Female", person.getGender());
	}
}