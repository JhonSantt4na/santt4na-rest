package com.jhonn.santt4na_rest.mapper.custon;

import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
	
	public PersonDTOV2 convertEntityToDTO(Person person){
		PersonDTOV2 dto = new PersonDTOV2();
		dto.setId(person.getId());
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setBirthDay(new Date());
		dto.setAddress(person.getAddress());
		dto.setGender(person.getGender());
		return dto;
	}
	
	public Person convertDTOToEntity(PersonDTOV2 personDTOV2){
		Person dto = new Person();
		dto.setId(personDTOV2.getId());
		dto.setFirstName(personDTOV2.getFirstName());
		dto.setLastName(personDTOV2.getLastName());
		//dto.setBirthDay(new Date());
		dto.setAddress(personDTOV2.getAddress());
		dto.setGender(personDTOV2.getGender());
		return dto;
	}
	
}
