package com.jhonn.santt4na_rest.dataDTO.v1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class BookDTO {
	
	private Long id;
	
	private String author;
	
	private LocalDateTime launch_date;
	
	private BigDecimal price;
	
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
	
	public LocalDateTime getLaunch_date() {
		return launch_date;
	}
	
	public void setLaunch_date(Date launch_date) {
		this.launch_date = launch_date;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
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
		if (!(o instanceof BookDTO bookDTO)) return false;
		return Objects.equals(getId(), bookDTO.getId()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLaunch_date(), bookDTO.getLaunch_date()) && Objects.equals(getPrice(), bookDTO.getPrice()) && Objects.equals(getTitle(), bookDTO.getTitle());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
	}
}
