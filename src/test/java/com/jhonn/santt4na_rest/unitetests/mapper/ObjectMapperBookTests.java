package com.jhonn.santt4na_rest.unitetests.mapper;

import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObject;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObjects;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;
import com.jhonn.santt4na_rest.model.Book;
import com.jhonn.santt4na_rest.unitetests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectMapperBookTests {
	
	MockBook inputObject;
	
	@BeforeEach
	public void setUp() {
		inputObject = new MockBook();
	}
	
	@Test
	public void parseEntityToDTOTest() {
		BookDTO output = parseObject(inputObject.mockEntity(), BookDTO.class);
		assertEquals(0L, output.getId());
		assertEquals("Author Test0", output.getAuthor());
		assertEquals(new Date(0L), output.getLaunchDate());
		assertEquals(25.0, output.getPrice());
		assertEquals("Title Test0", output.getTitle());
	}
	
	@Test
	public void parseEntityListToDTOListTest() {
		List<BookDTO> outputList = parseObjects(inputObject.mockEntityList(), BookDTO.class);
		
		BookDTO outputZero = outputList.get(0);
		assertEquals(0L, outputZero.getId());
		assertEquals("Author Test0", outputZero.getAuthor());
		assertEquals(new Date(0L), outputZero.getLaunchDate());
		assertEquals(25.0, outputZero.getPrice());
		assertEquals("Title Test0", outputZero.getTitle());
		
		BookDTO outputSeven = outputList.get(7);
		assertEquals(7L, outputSeven.getId());
		assertEquals("Author Test7", outputSeven.getAuthor());
		assertEquals(new Date(7 * 86400000L), outputSeven.getLaunchDate());
		assertEquals(25.0, outputSeven.getPrice());
		assertEquals("Title Test7", outputSeven.getTitle());
	}
	
	@Test
	public void parseDTOToEntityTest() {
		Book output = parseObject(inputObject.mockDTO(), Book.class);
		assertEquals(0L, output.getId());
		assertEquals("Author Test0", output.getAuthor());
		assertEquals(new Date(0L), output.getLaunchDate());
		assertEquals(25.0, output.getPrice());
		assertEquals("Title Test0", output.getTitle());
	}
	
	@Test
	public void parserDTOListToEntityListTest() {
		List<Book> outputList = parseObjects(inputObject.mockDTOList(), Book.class);
		
		Book outputZero = outputList.get(0);
		assertEquals(0L, outputZero.getId());
		assertEquals("Author Test0", outputZero.getAuthor());
		assertEquals(new Date(0L), outputZero.getLaunchDate());
		assertEquals(25.0, outputZero.getPrice());
		assertEquals("Title Test0", outputZero.getTitle());
		
		Book outputSeven = outputList.get(7);
		assertEquals(7L, outputSeven.getId());
		assertEquals("Author Test7", outputSeven.getAuthor());
		assertEquals(new Date(7 * 86400000L), outputSeven.getLaunchDate());
		assertEquals(25.0, outputSeven.getPrice());
		assertEquals("Title Test7", outputSeven.getTitle());
	}
}
