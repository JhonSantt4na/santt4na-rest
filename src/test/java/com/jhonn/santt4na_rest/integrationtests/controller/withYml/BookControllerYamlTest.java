package com.jhonn.santt4na_rest.integrationtests.controller.withYml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jhonn.santt4na_rest.config.TestConfigs;
import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.integrationtests.controller.withYml.mapper.YAMLMapper;
import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;
import com.jhonn.santt4na_rest.integrationtests.dto.wrappers.xml.PagedModelBook;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@Nested
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerYamlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YAMLMapper objectMapper;
	
	private static BookDTO book;
	
	@BeforeAll
	static void setUp() {
		objectMapper = new YAMLMapper();
		book = new BookDTO();
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
		specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_JHONN)
			.setBasePath("/api/book/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		var createdBook = given().config(
				RestAssuredConfig.config()
					.encoderConfig(
						EncoderConfig.encoderConfig().
							encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
			).spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.body(book, objectMapper)
			.when()
			.post()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(BookDTO.class, objectMapper);
		
		book = createdBook;
		
		assertNotNull(createdBook.getId());
		assertNotNull(book.getId());
		assertEquals("title_2d30330c88a7", book.getTitle());
		assertEquals("author_f1194253cae4", book.getAuthor());
		assertEquals(Double.valueOf(0.00), book.getPrice());
		
	}
	
	@Test
	@Order(2)
	void updateTest() throws JsonProcessingException {
		
		book.setTitle("Docker Deep Dive - Updated");
		
		var createdBook = given().config(
				RestAssuredConfig.config()
					.encoderConfig(
						EncoderConfig.encoderConfig().
							encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
			).spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.body(book, objectMapper)
			.when()
			.put()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(BookDTO.class, objectMapper);
		
		book = createdBook;
		
		assertNotNull(createdBook.getId());
		assertNotNull(book.getId());
		assertEquals("Docker Deep Dive - Updated", book.getTitle());
		assertEquals("author_f1194253cae4", book.getAuthor());
		assertEquals(Double.valueOf(0.00), book.getPrice());
		
	}
	
	@Test
	@Order(3)
	void findByIdTest() throws JsonProcessingException {
		
		var createdBook = given().config(
				RestAssuredConfig.config()
					.encoderConfig(
						EncoderConfig.encoderConfig().
							encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
			).spec(specification)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.pathParam("id", book.getId())
			.when()
			.get("{id}")
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(BookDTO.class, objectMapper);
		
		book = createdBook;
		
		assertNotNull(createdBook.getId());
		assertTrue(createdBook.getId() > 0);
		
		assertNotNull(createdBook.getId());
		assertNotNull(book.getId());
		assertEquals("Docker Deep Dive - Updated", book.getTitle());
		assertEquals("author_f1194253cae4", book.getAuthor());
		assertEquals(Double.valueOf(0.00), book.getPrice());
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
		
		var response = given(specification)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.queryParams("page", 0 , "size", 12, "direction", "asc")
			.when()
			.get()
			.then()
			.statusCode(200)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.extract()
			.body()
			.as(PagedModelBook.class, objectMapper);
		
		List<BookDTO> content = response.getContent();
		
		BookDTO foundBookOne = content.get(0);
		
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getTitle());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getPrice());
		assertTrue(foundBookOne.getId() > 0);
		assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", foundBookOne.getTitle());
		assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", foundBookOne.getAuthor());
		assertEquals(54.00, foundBookOne.getPrice());
		
		BookDTO foundBookFive = content.get(4);
		
		assertNotNull(foundBookFive.getId());
		assertNotNull(foundBookFive.getTitle());
		assertNotNull(foundBookFive.getAuthor());
		assertNotNull(foundBookFive.getPrice());
		assertTrue(foundBookFive.getId() > 0);
		assertEquals("Domain Driven Design", foundBookFive.getTitle());
		assertEquals("Eric Evans", foundBookFive.getAuthor());
		assertEquals(92.00, foundBookFive.getPrice());
	}
	
}