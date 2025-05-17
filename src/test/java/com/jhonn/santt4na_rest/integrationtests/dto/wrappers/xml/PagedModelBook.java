package com.jhonn.santt4na_rest.integrationtests.dto.wrappers.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jhonn.santt4na_rest.integrationtests.dto.BookDTO;

import java.io.Serializable;
import java.util.List;

public class PagedModelBook implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "content")
	public List<BookDTO> content;
	
	public PagedModelBook() {
	}
	
	public List<BookDTO> getContent() {
		return content;
	}
	
	public void setContent(List<BookDTO> content) {
		this.content = content;
	}
}
