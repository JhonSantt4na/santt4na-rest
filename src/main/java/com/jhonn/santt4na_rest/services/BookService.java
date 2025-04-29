package com.jhonn.santt4na_rest.services;

import com.jhonn.santt4na_rest.controllers.BookController;
import com.jhonn.santt4na_rest.controllers.PersonController;
import com.jhonn.santt4na_rest.dataDTO.v1.BookDTO;
import com.jhonn.santt4na_rest.mapper.custon.PersonMapper;
import com.jhonn.santt4na_rest.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
	
	private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());
	
	@Autowired
	PersonMapper converter;
	
	@Autowired
	private BookRepository repository;
	
	
	public List<BookDTO> findAll() {
		return
	}
	
	public BookDTO findById(Long id) {
	}
	
	public BookDTO create(BookDTO book) {
	}
	
	public BookDTO update(BookDTO book) {
	}
	
	public void delete(Long id) {
	}
	
	private static void addHateoasLinks(BookDTO dto) {
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).create(dto)).withSelfRel().withType("POST"));
		dto.add(linkTo(methodOn(BookController.class).update(dto)).withSelfRel().withType("PUT"));
		dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withSelfRel().withType("DELETE"));
	}
}
