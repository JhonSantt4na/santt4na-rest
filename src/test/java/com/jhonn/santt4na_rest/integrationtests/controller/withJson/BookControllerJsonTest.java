package com.jhonn.santt4na_rest.integrationtests.controller.withJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhonn.santt4na_rest.config.TestConfigs;
import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;
import com.jhonn.santt4na_rest.integrationtests.dto.wrappers.json.WrapperBookDTO;
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
import java.util.List;

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
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date expectedDate = sdf.parse("2025-05-17 17:09:40");
	
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
		
		assertNotNull(createdBook.getId());
		assertTrue(createdBook.getId() > 0);
		
		assertEquals("author_f1194253cae4", createdBook.getAuthor());
		assertEquals("title_2d30330c88a7", createdBook.getTitle());
		assertEquals(expectedDate, createdBook.getLaunchDate());
		assertEquals(Double.valueOf(0.00), createdBook.getPrice());
		
	}
	
	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		book.setAuthor("Benedict Torvalds");
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(book)
			.when()
			.put()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
		book = createdBook;
		
		assertNotNull(createdBook.getId());
		assertTrue(createdBook.getId() > 0);
		
		assertEquals("Benedict Torvalds", createdBook.getAuthor());
		assertEquals("title_2d30330c88a7", createdBook.getTitle());
		assertEquals(expectedDate, createdBook.getLaunchDate());
		assertEquals(Double.valueOf(0.00), createdBook.getPrice());
	}
	
	@Test
	@Order(3)
	void findByIdTest() throws JsonProcessingException, ParseException {
		
		var content = given(specification)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.pathParam("id", book.getId())
			.when()
			.get("{id}")
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
		book = createdBook;
		
		book.setLaunchDate(expectedDate);
		
		
		assertNotNull(createdBook.getId());
		assertTrue(createdBook.getId() > 0);
		
		assertEquals("Benedict Torvalds", createdBook.getAuthor());
		assertEquals("title_2d30330c88a7", createdBook.getTitle());
		assertEquals(expectedDate, createdBook.getLaunchDate());
		assertEquals(Double.valueOf(0.00), createdBook.getPrice());
	}
	
	@Test
	@Order(4)
	void deleteTest() throws JsonProcessingException {
		
		given(specification)
			.pathParam("id", book.getId())
			.when()
			.delete("{id}")
			.then()
			.statusCode(204);
	}
	
	@Test
	@Order(5)
	void findAllTest() throws JsonProcessingException {
		
		var content = given(specification)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.queryParam("page", 0, "size", 4, "direction", "asc")
			.when()
			.get()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.extract()
			.body()
			.asString();
		
		WrapperBookDTO wrapper = objectMapper.readValue(content, WrapperBookDTO.class);
		List<BookDTO> books = wrapper.getEmbedded().getBook();
		
		BookDTO bookOne = books.get(0);
		
		assertNotNull(bookOne.getId());
		assertTrue(bookOne.getId() > 0);
		
		assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", bookOne.getAuthor());
		assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", bookOne.getTitle());
		assertEquals(1510023600000L, bookOne.getLaunchDate().getTime());
		assertEquals(Double.valueOf(54.0), bookOne.getPrice());
		
		BookDTO bookTwo = books.get(1);
		
		assertNotNull(bookTwo.getId());
		assertTrue(bookTwo.getId() > 0);
		assertEquals("Robert C. Martin", bookTwo.getAuthor());
		assertEquals("Clean Code", bookTwo.getTitle());
		assertEquals(1231556400000L, bookTwo.getLaunchDate().getTime());
		assertEquals(Double.valueOf(77.0), bookTwo.getPrice());
	}
	
}