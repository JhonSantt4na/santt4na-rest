package com.jhonn.santt4na_rest.integrationtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "book") // nome do elemento raiz no XML
@XmlAccessorType(XmlAccessType.FIELD)
public class BookDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String author;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date launchDate;
	private Double price;
	private String title;
	
	public BookDTO() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Date getLaunchDate() {
		return launchDate;
	}
	
	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof com.jhonn.santt4na_rest.dataDTO.v1.BookDTO bookDTO)) return false;
		if (!super.equals(o)) return false;
		return Objects.equals(getId(), bookDTO.getId()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLaunchDate(), bookDTO.getLaunchDate()) && Objects.equals(getPrice(), bookDTO.getPrice()) && Objects.equals(getTitle(), bookDTO.getTitle());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getId(), getAuthor(), getLaunchDate(), getPrice(), getTitle());
	}
}
