package com.jhonn.santt4na_rest.integrationtests.controller.cors.withJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jhonn.santt4na_rest.config.TestConfigs;
import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.integrationtests.dto.PersonDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ordenando pois o JUnit roda sem order
class PersonControllerCorsTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonDTO person;
	
	@BeforeAll // Para todos os testes
	static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		/*
		Quando fazemos uma req para um endPoint nao precisamos dos links do HATEOAS
		precisamos remover ele para os testes usamos o DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
		*/
		
		person = new PersonDTO();
	}
	
	@Test
	@Order(1)
	void create() throws JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_JHONN)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(person)
			.when()
				.post()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		
		assertTrue(createdPerson.getId() > 0);
		
		
		assertEquals("Maicon", createdPerson.getFirstName());
		assertEquals("Jackson", createdPerson.getLastName());
		assertEquals("Rua B", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
		
	}
	
	@Test
	@Order(2)
	void createWithWrongOrigin() throws JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SANTT4NA)
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(person)
			.when()
			.post()
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString();
		
		assertEquals("Invalid CORS request", content);
		
	}
	
	
	@Test
	@Order(3)
	void findById() throws JsonProcessingException {
		
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_JHONN)
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", person.getId())
			.when()
			.get("{id}")
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		
		assertTrue(createdPerson.getId() > 0);
		
		
		assertEquals("Maicon", createdPerson.getFirstName());
		assertEquals("Jackson", createdPerson.getLastName());
		assertEquals("Rua B", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
	}
	
	@Test
	@Order(4)
	void findByIdWrongOrigin() throws JsonProcessingException {
		
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SANTT4NA)
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", person.getId())
			.when()
			.get("{id}")
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString();
		
		assertEquals("Invalid CORS request", content);
	}
	
	private void mockPerson() {
		person.setFirstName("Maicon");
		person.setLastName("Jackson");
		person.setAddress("Rua B");
		person.setGender("Male");
		person.setEnabled(true);
	}
}