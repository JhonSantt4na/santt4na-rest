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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	@Disabled("REASON: Still Under Development")
	void findAll() {
		List<Book> list = input.mockEntityList();
		when(repository.findAll()).thenReturn(list);
		List<BookDTO> books = new ArrayList<>();//service.findAll();
		
		assertNotNull(books);
		assertEquals(14, books.size());
		
		var bookOne = books.get(1);
		
		assertNotNull(bookOne);
		assertNotNull(bookOne.getId());
		assertNotNull(bookOne.getLinks());
		
		assertNotNull(bookOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(bookOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(bookOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(bookOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(bookOne.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/1")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test1", bookOne.getAuthor());
		assertEquals(25D, bookOne.getPrice());
		assertEquals("Title Test1", bookOne.getTitle());
		assertNotNull(bookOne.getLaunchDate());
		
		var bookFour = books.get(4);
		
		assertNotNull(bookFour);
		assertNotNull(bookFour.getId());
		assertNotNull(bookFour.getLinks());
		
		assertNotNull(bookFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/4")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(bookFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(bookFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(bookFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(bookFour.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/4")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test4", bookFour.getAuthor());
		assertEquals(25D, bookFour.getPrice());
		assertEquals("Title Test4", bookFour.getTitle());
		assertNotNull(bookFour.getLaunchDate());
		
		var bookSeven = books.get(7);
		
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getId());
		assertNotNull(bookSeven.getLinks());
		
		assertNotNull(bookSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("self")
				&& link.getHref().endsWith("/api/book/v1/7")
				&& link.getType().equals("GET")
			));
		
		assertNotNull(bookSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("findAll")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("GET")
			)
		);
		
		assertNotNull(bookSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("create")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("POST")
			)
		);
		
		assertNotNull(bookSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("update")
				&& link.getHref().endsWith("/api/book/v1")
				&& link.getType().equals("PUT")
			)
		);
		
		assertNotNull(bookSeven.getLinks().stream()
			.anyMatch(link -> link.getRel().value().equals("delete")
				&& link.getHref().endsWith("/api/book/v1/7")
				&& link.getType().equals("DELETE")
			)
		);
		
		assertEquals("Author Test7", bookSeven.getAuthor());
		assertEquals(25D, bookSeven.getPrice());
		assertEquals("Title Test7", bookSeven.getTitle());
		assertNotNull(bookFour.getLaunchDate());
	}
}
