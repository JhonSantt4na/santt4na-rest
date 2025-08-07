package com.jhonn.santt4na_rest.unitetests.services;

import com.jhonn.santt4na_rest.dataDTO.v1.BookDTO;
import com.jhonn.santt4na_rest.exceptions.RequiredObjectIsNullException;
import com.jhonn.santt4na_rest.service.BookService;
import com.jhonn.santt4na_rest.unitetests.mapper.mocks.MockBook;
import com.jhonn.santt4na_rest.model.Book;
import com.jhonn.santt4na_rest.repository.BookRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {
	
	private final MockBook input = new MockBook();
	
	@Mock
	private BookRepository repository;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	PagedResourcesAssembler<BookDTO> assembler;
	
	@Test
	void findById() {
		
		Book book = input.mockEntity(1);
		book.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(book));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void create() {
		Book book = input.mockEntity(1);
		Book persisted = book;
		persisted.setId(1L);
		
		BookDTO dto = input.mockDTO(1);
		
		when(repository.save(any(Book.class))).thenReturn(persisted);
		
		var result = service.create(dto);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class,
			() -> {
				service.create(null);
			});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void update() {
		Book book = input.mockEntity(1);
		Book persisted = book;
		persisted.setId(1L);
		
		BookDTO dto = input.mockDTO(1);
		
		when(repository.findById(1L)).thenReturn(Optional.of(book));
		when(repository.save(book)).thenReturn(persisted);
		
		var result = service.update(dto);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(result.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(25D, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class,
			() -> {
				service.update(null);
			});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void delete() {
		Book book = input.mockEntity(1);
		book.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(book));
		
		service.delete(1L);
		verify(repository, times(1)).findById(anyLong());
		verify(repository, times(1)).delete(any(Book.class));
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void findAll() {
		// Mocking repository access
		List<Book> mockEntityList = input.mockEntityList();
		Page<Book> mockPage = new PageImpl<>(mockEntityList);
		when(repository.findAll(any(Pageable.class))).thenReturn(mockPage);
		
		List<BookDTO> mockDtoList = input.mockDTOList();
		
		// Mocking assembler
		// assembler.toModel(booksWithLinks, findAllLink);
		List<EntityModel<BookDTO>> entityModels = mockDtoList.stream()
			.map(EntityModel::of)
			.collect(Collectors.toList());
		
		PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
			mockPage.getSize(),
			mockPage.getNumber(),
			mockPage.getTotalElements(),
			mockPage.getTotalPages()
		);
		
		PagedModel<EntityModel<BookDTO>> mockPagedModel = PagedModel.of(entityModels, pageMetadata);
		when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(mockPagedModel);
		
		
		// Executing fid all
		PagedModel<EntityModel<BookDTO>> result = service.findAll(PageRequest.of(0, 14));
		
		List<BookDTO> books = result.getContent()
			.stream()
			.map(EntityModel::getContent)
			.collect(Collectors.toList());
		
		assertNotNull(books);
		assertEquals(14, books.size());
		
		validateIndividualBook(books.get(1), 1);
		validateIndividualBook(books.get(4), 4);
		validateIndividualBook(books.get(7), 7);
	}
	
	private static void validateIndividualBook(BookDTO book, int i) {
		assertNotNull(book);
		assertNotNull(book.getId());
		assertNotNull(book.getLinks());
		
		assertNotNull(book.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/" + i)
				&& link.getType().equals("GET")
			));
		
		assertNotNull(book.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(book.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(book.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(book.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/" + i)
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test" + i, book.getAuthor());
		assertEquals(25D, book.getPrice());
		assertEquals("Title Test" + i, book.getTitle());
		assertNotNull(book.getLaunchDate());
	}
}
