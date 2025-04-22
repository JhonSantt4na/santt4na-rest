package com.jhonn.santt4na_rest.unitariosTestes;

import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.mocks.MockPerson;
import com.jhonn.santt4na_rest.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObject;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObjects;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ObjectMapperTests {
	MockPerson inputObject;
	
	@BeforeEach
	public void setUp() {
		inputObject = new MockPerson();
	}
	
	@Test
	public void parseEntityToDTOTest() {
		PersonDTO output = parseObject(inputObject.mockEntity(), PersonDTO.class);
		assertEquals(Long.valueOf(0L), output.getId());
		assertEquals("First Name Test0", output.getFirstName());
		assertEquals("Last Name Test0", output.getLastName());
		assertEquals("Address Test0", output.getAddress());
		assertEquals("Male", output.getGender());
	}
	
	@Test
	public void parseEntityListToDTOListTest() {
		List<PersonDTO> outputList = parseObjects(inputObject.mockEntityList(), PersonDTO.class);
		PersonDTO outputZero = outputList.getFirst();
		
		assertEquals(Long.valueOf(0L), outputZero.getId());
		assertEquals("First Name Test0", outputZero.getFirstName());
		assertEquals("Last Name Test0", outputZero.getLastName());
		assertEquals("Address Test0", outputZero.getAddress());
		assertEquals("Male", outputZero.getGender());
		
		PersonDTO outputSeven = outputList.get(7);
		
		assertEquals(Long.valueOf(7L), outputSeven.getId());
		assertEquals("First Name Test7", outputSeven.getFirstName());
		assertEquals("Last Name Test7", outputSeven.getLastName());
		assertEquals("Address Test7", outputSeven.getAddress());
		assertEquals("Female", outputSeven.getGender());
		
		PersonDTO outputTwelve = outputList.get(12);
		
		assertEquals(Long.valueOf(12L), outputTwelve.getId());
		assertEquals("First Name Test12", outputTwelve.getFirstName());
		assertEquals("Last Name Test12", outputTwelve.getLastName());
		assertEquals("Address Test12", outputTwelve.getAddress());
		assertEquals("Male", outputTwelve.getGender());
	}
	
	@Test
	public void parseDTOToEntityTest() {
		Person output = parseObject(inputObject.mockDTO(), Person.class);
		assertEquals(Long.valueOf(0L), output.getId());
		assertEquals("First Name Test0", output.getFirstName());
		assertEquals("Last Name Test0", output.getLastName());
		assertEquals("Address Test0", output.getAddress());
		assertEquals("Male", output.getGender());
	}
	
	@Test
	public void parserDTOListToEntityListTest() {
		List<Person> outputList = parseObjects(inputObject.mockDTOList(), Person.class);
		Person outputZero = outputList.getFirst();
		
		assertEquals(Long.valueOf(0L), outputZero.getId());
		assertEquals("First Name Test0", outputZero.getFirstName());
		assertEquals("Last Name Test0", outputZero.getLastName());
		assertEquals("Address Test0", outputZero.getAddress());
		assertEquals("Male", outputZero.getGender());
		
		Person outputSeven = outputList.get(7);
		
		assertEquals(Long.valueOf(7L), outputSeven.getId());
		assertEquals("First Name Test7", outputSeven.getFirstName());
		assertEquals("Last Name Test7", outputSeven.getLastName());
		assertEquals("Address Test7", outputSeven.getAddress());
		assertEquals("Female", outputSeven.getGender());
		
		Person outputTwelve = outputList.get(12);
		
		assertEquals(Long.valueOf(12L), outputTwelve.getId());
		assertEquals("First Name Test12", outputTwelve.getFirstName());
		assertEquals("Last Name Test12", outputTwelve.getLastName());
		assertEquals("Address Test12", outputTwelve.getAddress());
		assertEquals("Male", outputTwelve.getGender());
	}
}