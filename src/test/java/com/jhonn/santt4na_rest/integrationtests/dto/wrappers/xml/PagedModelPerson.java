package com.jhonn.santt4na_rest.integrationtests.dto.wrappers.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jhonn.santt4na_rest.integrationtests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PagedModelPerson implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "content")
	public List<PersonDTO> content;
	
	public PagedModelPerson() {
	}
	
	public List<PersonDTO> getContent() {
		return content;
	}
	
	public void setContent(List<PersonDTO> content) {
		this.content = content;
	}
}
