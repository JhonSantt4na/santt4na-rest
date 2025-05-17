package com.jhonn.santt4na_rest.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;
import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("people")
	private List<BookDTO> people;
	
	public BookEmbeddedDTO() {
	}
	
	public List<BookDTO> getPeople() {
		return people;
	}
	
	public void setPeople(List<BookDTO> people) {
		this.people = people;
	}
}
