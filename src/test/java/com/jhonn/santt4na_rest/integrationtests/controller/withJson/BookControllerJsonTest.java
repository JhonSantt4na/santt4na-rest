package com.jhonn.santt4na_rest.integrationtests.controller.withJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhonn.santt4na_rest.config.TestConfigs;
import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static BookDTO book;
	
	public BookControllerJsonTest() throws ParseException {
	}
	
	@BeforeAll
	static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		book = new BookDTO();
		
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_JHONN)
			.setBasePath("/api/book/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
	}
	
	private void mockBook() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2025-05-17 17:09:40");
		
		book.setAuthor("author_f1194253cae4");
		book.setLaunchDate(date);
		book.setPrice(0.00);
		book.setTitle("title_2d30330c88a7");
	}
	
	
	@Test
	@Order(1)
	void createTest() throws JsonProcessingException, ParseException {
		mockBook();
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(book)
			.when()
			.post()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
		book = createdBook;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date expectedDate = sdf.parse("2025-05-17 17:09:40");
		
		
		assertNotNull(createdBook.getId());
		assertTrue(createdBook.getId() > 0);
		
		assertEquals("author_f1194253cae4", createdBook.getAuthor());
		assertEquals("title_2d30330c88a7", createdBook.getTitle());
		assertEquals(expectedDate, createdBook.getLaunchDate());
		assertEquals(Double.valueOf(0.00), createdBook.getPrice());
		
		
	}
}

/*
class PersonControllerJsonTest  {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonDTO person;
	
	
	
	
	
	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		person.setLastName("Benedict Torvalds");
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(person)
			.when()
			.put()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
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
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", person.getId())
			.when()
			.get("{id}")
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
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
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", person.getId())
			.when()
			.patch("{id}")
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
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
		
		var content = given(specification)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.queryParam("page", 3, "size", 12, "direction", "asc")
			.when()
			.get()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
		List<PersonDTO> people = wrapper.getEmbedded().getPeople();
		
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
*/
