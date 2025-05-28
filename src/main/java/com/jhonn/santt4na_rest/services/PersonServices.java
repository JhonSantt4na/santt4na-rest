package com.jhonn.santt4na_rest.services;

import com.jhonn.santt4na_rest.controllers.PersonController;
import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.dataDTO.v2.PersonDTOV2;
import com.jhonn.santt4na_rest.exceptions.BadRequestException;
import com.jhonn.santt4na_rest.exceptions.FileStorageException;
import com.jhonn.santt4na_rest.exceptions.RequiredObjectIsNullException;
import com.jhonn.santt4na_rest.exceptions.ResourceNotFoundException;
import static com.jhonn.santt4na_rest.mapper.ObjectMapper.parseObject;

import com.jhonn.santt4na_rest.file.exporter.MediaTypes;
import com.jhonn.santt4na_rest.file.exporter.contract.FileExporter;
import com.jhonn.santt4na_rest.file.exporter.factory.FileExporterFactory;
import com.jhonn.santt4na_rest.file.importer.contract.FileImporter;
import com.jhonn.santt4na_rest.file.importer.factory.FileImporterFactory;
import com.jhonn.santt4na_rest.mapper.custon.PersonMapper;
import com.jhonn.santt4na_rest.model.Person;
import com.jhonn.santt4na_rest.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Service
public class PersonServices {
	
	private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
	@Autowired
	PersonMapper converter;
	
	private final PersonRepository repository;
	
	@Autowired
	FileImporterFactory importer;
	
	@Autowired
	FileExporterFactory exporter;
	
	public PersonServices(PersonRepository repository) {
		this.repository = repository;
	}
	
	@Autowired
	PagedResourcesAssembler<PersonDTO> assembler;
	
	public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable){
		logger.info("Finding all Person!");
		
		var people = repository.findAll(pageable);
		return buildPagedModel(pageable, people);
	}
	
	public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable){
		logger.info("Finding People By Name");
		
		var people = repository.findPeopleByName(firstName,pageable);
		return buildPagedModel(pageable, people);
	}
	
	public Resource exportPerson(Long id, String acceptHeader){
		logger.info("Exporting data of one Person!");
		
		var person = repository.findById(id)
			.map(entity -> parseObject(entity, PersonDTO.class))
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		
		
		try {
			FileExporter exporter = this.exporter.getExporter(acceptHeader);
			return exporter.exportPerson(person);
			
		} catch (Exception e) {
			throw new RuntimeException("Error during file export!", e);
		}
	}
	
	public PersonDTO findById(Long id){
		logger.info("Finding one Person!");
		var entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		var dto = parseObject(entity, PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public Resource exportPage(Pageable pageable, String acceptHeader) {
		logger.info("Exporting a People page");
		
		Page<PersonDTO> dtoPage = repository.findAll(pageable)
			.map(person -> parseObject(person, PersonDTO.class));
		
		List<PersonDTO> people = dtoPage.getContent();
		
		try {
			FileExporter exporter = this.exporter.getExporter(acceptHeader);
			return exporter.exportFile(people);
		} catch (Exception e) {
			throw new RuntimeException("Error during file export!", e);
		}
	}
	
	public PersonDTO create(PersonDTO person) {
		logger.info("Creating one Person!");
		if (person == null) {
			throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
		}
		var entity = parseObject(person, Person.class);
		var dto = parseObject(repository.save(entity), PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	// Create V2
	public PersonDTOV2 createV2(PersonDTOV2 person) {
		logger.info("Creating one Person V2!");
		var entity = converter.convertDTOToEntity(person);
		return converter.convertEntityToDTO(repository.save(entity));
	}
	
	public List<PersonDTO> massCreation(MultipartFile file) {
		logger.info("Importing People from file!");
		
		if (file.isEmpty()){
			throw new BadRequestException(" Please set a valid file");
		}
		
		try(InputStream inputStream = file.getInputStream()) {
			
			String fileName = Optional.ofNullable(file.getOriginalFilename())
				.orElseThrow(()->new BadRequestException("Filename be cannot null"));
			
			FileImporter importer = this.importer.getImporter(fileName);
			
			List<Person> entities = importer.importFile(inputStream)
				.stream()
				.map(dto -> repository.save(parseObject(dto, Person.class)))
				.toList();
			
			return entities.stream()
				.map(entity -> {
				var dto = parseObject(entity, PersonDTO.class);
				addHateoasLinks(dto);
				return dto;
			}).toList();
			
		} catch (Exception e) {
			throw new FileStorageException("Error processing the file!");
		}
	}
	
	public PersonDTO update(PersonDTO person) {
		logger.info("Updating one Person!");
		if (person == null) {
			throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
		}
		Person entity = repository.findById(person.getId())
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var dto = parseObject(repository.save(entity), PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
		
	}
	
	@Transactional
	public PersonDTO disablePerson(Long id){
		logger.info("Disabling one Person!");
		
		repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		repository.disablePerson(id);
		var entity = repository.findById(id).get();
		
		var dto = parseObject(entity, PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public void delete(Long id){
		logger.info("Deleting one Person!");
		Person entity = repository.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
		repository.delete(entity);
	}
	
	private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {
		var peopleWithLinks = people.map(person -> {
			var dto = parseObject(person, PersonDTO.class);
			addHateoasLinks(dto);
			return dto;
		});
		Link findAllLink = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(PersonController.class)
				.findAll(
					pageable.getPageNumber(),
					pageable.getPageSize(),
					String.valueOf(pageable.getSort()))).withSelfRel();
		
		return assembler.toModel(peopleWithLinks, findAllLink);
	}
	
	private static void addHateoasLinks(PersonDTO dto) {
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withRel("findById").withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).findByName("", 1, 12, "asc")).withRel("findByName").withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
		dto.add(linkTo(methodOn(PersonController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
		dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
		dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
		dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
		
		dto.add(linkTo(methodOn(PersonController.class)
			.exportPage(1, 12, "asc", null))
			.withRel("exportPage")
			.withType("GET")
			.withTitle("Export People")
		);
	}
	
}

