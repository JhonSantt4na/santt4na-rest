package com.jhonn.santt4na_rest.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;
import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("bookDTOList")
	private List<BookDTO> book;
	
	public BookEmbeddedDTO() {
	}
	
	public List<BookDTO> getBook() {
		return book;
	}
	
	public void setBook(List<BookDTO> book) {
		this.book = book;
	}
}
