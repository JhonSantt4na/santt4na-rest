package com.jhonn.santt4na_rest.dataDTO.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.jhonn.santt4na_rest.model.Book;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Relation(collectionRelation = "people")
@JacksonXmlRootElement(localName = "Person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
	
	private static final long serialversionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private Date birthDay;
	private String gender;
	private Boolean enabled;
	private String sensitiveData;
	private String profileUrl;
	private String photoUrl;
	@JsonIgnore
	private List<Book> books;
	
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
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getProfileUrl() {
		return profileUrl;
	}
	
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	@JsonIgnore
	public String getName(){
		return (firstName != null ? firstName : "") +
			(lastName != null ? firstName : "") ;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PersonDTO personDTO = (PersonDTO) o;
		return Objects.equals(id, personDTO.id) && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(phoneNumber, personDTO.phoneNumber) && Objects.equals(address, personDTO.address) && Objects.equals(birthDay, personDTO.birthDay) && Objects.equals(gender, personDTO.gender) && Objects.equals(enabled, personDTO.enabled) && Objects.equals(sensitiveData, personDTO.sensitiveData) && Objects.equals(profileUrl, personDTO.profileUrl) && Objects.equals(photoUrl, personDTO.photoUrl) && Objects.equals(books, personDTO.books);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, firstName, lastName, phoneNumber, address, birthDay, gender, enabled, sensitiveData, profileUrl, photoUrl, books);
	}
}
