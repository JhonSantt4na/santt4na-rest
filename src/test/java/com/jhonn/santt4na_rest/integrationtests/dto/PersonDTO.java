package com.jhonn.santt4na_rest.integrationtests.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PersonDTO implements Serializable {
	
	private static final long serialversionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private Date birthDay;
	private String gender;
	private String sensitiveData;
	
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
	
	public Date getBirthDay() {
		return birthDay;
	}
	
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getSensitiveData() {
		return sensitiveData;
	}
	
	public void setSensitiveData(String sensitiveData) {
		this.sensitiveData = sensitiveData;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO personDTO)) return false;
		return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getBirthDay(), personDTO.getBirthDay()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getSensitiveData(), personDTO.getSensitiveData());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber(), getAddress(), getBirthDay(), getGender(), getSensitiveData());
	}
}