package com.jhonn.santt4na_rest.services;


import com.jhonn.santt4na_rest.controllers.BookController;
import com.jhonn.santt4na_rest.dataDTO.v1.BookDTO;
import com.jhonn.santt4na_rest.exceptions.RequiredObjectIsNullException;
import com.jhonn.santt4na_rest.exceptions.ResourceNotFoundException;

import com.jhonn.santt4na_rest.model.Book;
import com.jhonn.santt4na_rest.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObject;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class BookServices {
	
	private final Logger logger = LoggerFactory.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	public List<BookDTO> findAll(){
		logger.info("Finding all Books!");
		var persons = parseObjects(repository.findAll(), BookDTO.class);
		persons.forEach(BookServices::addHateoasLinks);
		return persons;
	}
	
	public BookDTO findById(Long id){
		logger.info("Finding one Book!");
		var entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		var dto = parseObject(entity, BookDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public BookDTO create(BookDTO book) {
		if (book == null) {
			throw new RequiredObjectIsNullException();
		}
		logger.info("Creating one Book!");
		var entity = parseObject(book, Book.class);
		var dto = parseObject(repository.save(entity), BookDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public BookDTO update(BookDTO book) {
		logger.info("Updating one Book!");
		if (book == null) {
			throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
		}
		Book entity = repository.findById(book.getId())
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var dto = parseObject(repository.save(entity), BookDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public void delete(Long id){
		logger.info("Deleting one Book!");
		Book entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		repository.delete(entity);
	}
	
	
	private static void addHateoasLinks(BookDTO dto) {
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).create(dto)).withSelfRel().withType("POST"));
		dto.add(linkTo(methodOn(BookController.class).update(dto)).withSelfRel().withType("PUT"));
		dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withSelfRel().withType("DELETE"));
	}
	
}

