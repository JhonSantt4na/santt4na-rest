package com.jhonn.santt4na_rest.model;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
	private static final long serialversionUID = 1l;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String geder;
	
	public Person() {
	}
	
	public Person(Long id, String firstName, String lastName, String address, String geder) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.geder = geder;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getGeder() {
		return geder;
	}
	
	public void setGeder(String geder) {
		this.geder = geder;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Person person)) return false;
		return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress()) && Objects.equals(getGeder(), person.getGeder());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGeder());
	}
}
