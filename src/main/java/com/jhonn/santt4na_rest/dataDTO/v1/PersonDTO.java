package com.jhonn.santt4na_rest.dataDTO.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "address","first_name", "last_name", "gender"}) // Selection order the attribute
public class PersonDTO implements Serializable {
	
	private static final long serialversionUID = 1l;
	
	private Long id;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name") // Rename the attribute
	private String lastName;
	private String address;
	
	@JsonIgnore //Ignore the attribute
	private String gender;
	
	public PersonDTO() {
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
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof com.jhonn.santt4na_rest.model.Person person)) return false;
		return Objects.equals(getId(), person.getId()) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getAddress(), person.getAddress()) && Objects.equals(getGender(), person.getGender());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
	}
}
