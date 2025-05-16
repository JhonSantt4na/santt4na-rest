package com.jhonn.santt4na_rest.integrationtests.controller.withYml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jhonn.santt4na_rest.config.TestConfigs;
import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.integrationtests.controller.withYml.mapper.YAMLMapper;
import com.jhonn.santt4na_rest.integrationtests.dto.PersonDTO;
import com.jhonn.santt4na_rest.integrationtests.dto.wrappers.xml.PagedModelPerson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerYmlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YAMLMapper objectMapper;
	
	private static PersonDTO person;
	
	@BeforeAll
	static void setUp() {
		objectMapper = new YAMLMapper();
		
		person = new PersonDTO();
		
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_JHONN)
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
	}
	
	@Test
	@Order(1)
	void createTest() throws JsonProcessingException {
		mockPerson();
		
		var createdPerson = given().config(
				RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
			.spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.body(person, objectMapper)
			.when()
			.post()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(PersonDTO.class, objectMapper);
		
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("Linux", createdPerson.getFirstName());
		assertEquals("Stallman", createdPerson.getLastName());
		assertEquals("Rua C", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
		
	}
	
	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		person.setLastName("Benedict Torvalds");
		
		var createdPerson = given().config(
				RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
			.spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.body(person, objectMapper)
			.when()
			.put()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(PersonDTO.class, objectMapper);
		
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("Linux", createdPerson.getFirstName());
		assertEquals("Benedict Torvalds", createdPerson.getLastName());
		assertEquals("Rua C", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
		
	}
	
	@Test
	@Order(3)
	void findByIdTest() throws JsonProcessingException {
		
		var createdPerson = given().config(
				RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
			.spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.pathParam("id", person.getId())
			.when()
			.get("{id}")
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(PersonDTO.class, objectMapper);
		
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("Linux", createdPerson.getFirstName());
		assertEquals("Benedict Torvalds", createdPerson.getLastName());
		assertEquals("Rua C", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());
	}
	
	@Test
	@Order(4)
	void disableTest() throws JsonProcessingException {
		
		var createdPerson = given().config(
				RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
			.spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.pathParam("id", person.getId())
			.when()
			.patch("{id}")
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(PersonDTO.class, objectMapper);
		
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("Linux", createdPerson.getFirstName());
		assertEquals("Benedict Torvalds", createdPerson.getLastName());
		assertEquals("Rua C", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
		assertFalse(createdPerson.getEnabled());
	}
	
	@Test
	@Order(5)
	void deleteTest() throws JsonProcessingException {
		
		given(specification)
			.pathParam("id", person.getId())
			.when()
			.delete("{id}")
			.then()
			.statusCode(204);
	}
	
	private void mockPerson() {
		person.setFirstName("Linux");
		person.setLastName("Stallman");
		person.setAddress("Rua C");
		person.setGender("Male");
		person.setEnabled(true);
	}
	
	@Test
	@Order(6)
	void findAllTest() throws JsonProcessingException {
		
		var response = given().config(
				RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
			.spec(specification)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.queryParam("page", 3, "size", 12, "direction", "asc")
			.when()
			.get()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(PagedModelPerson.class, objectMapper);
		
		List<PersonDTO> people = response.getContent();
		
		PersonDTO personOne = people.get(0);
		
		assertNotNull(personOne.getId());
		assertTrue(personOne.getId() > 0);
		
		assertEquals("Allin", personOne.getFirstName());
		assertEquals("Otridge", personOne.getLastName());
		assertEquals("09846 Independence Center", personOne.getAddress());
		assertEquals("Male", personOne.getGender());
		assertFalse(personOne.getEnabled());
		
		PersonDTO personFour = people.get(4);
		
		assertNotNull(personFour.getId());
		assertTrue(personFour.getId() > 0);
		
		assertEquals("Alonso", personFour.getFirstName());
		assertEquals("Luchelli", personFour.getLastName());
		assertEquals("9 Doe Crossing Avenue", personFour.getAddress());
		assertEquals("Male", personFour.getGender());
		assertFalse(personFour.getEnabled());
		
	}
	
}