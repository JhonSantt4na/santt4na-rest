package com.jhonn.santt4na_rest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Books {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String author;
	
	@Column(nullable = false)
	private LocalDateTime launch_date;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private String title;
	
	public Books() {
	}
	
	public Books(Long id, String author, LocalDateTime launch_date, BigDecimal price, String title) {
		this.id = id;
		this.author = author;
		this.launch_date = launch_date;
		this.price = price;
		this.title = title;
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
	
	public void setLaunch_date(LocalDateTime launch_date) {
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
		if (!(o instanceof Books books)) return false;
		return Objects.equals(getId(), books.getId()) && Objects.equals(getAuthor(), books.getAuthor()) && Objects.equals(getLaunch_date(), books.getLaunch_date()) && Objects.equals(getPrice(), books.getPrice()) && Objects.equals(getTitle(), books.getTitle());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
	}
}
