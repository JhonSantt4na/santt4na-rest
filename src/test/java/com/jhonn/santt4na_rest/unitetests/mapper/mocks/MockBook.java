package com.jhonn.santt4na_rest.unitetests.mapper.mocks;

import com.jhonn.santt4na_rest.dataDTO.v1.BookDTO;
import com.jhonn.santt4na_rest.model.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {
	
	private static final long ONE_DAY_IN_MILLIS = 86_400_000L; // 24h em milissegundos
	
	public Book mockEntity() {
		return mockEntity(0);
	}
	
	public BookDTO mockDTO() {
		return mockDTO(0);
	}
	
	public List<Book> mockEntityList() {
		List<Book> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}
	
	public List<BookDTO> mockDTOList() {
		List<BookDTO> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockDTO(i));
		}
		return books;
	}
	
	public Book mockEntity(Integer number) {
		Book book = new Book();
		book.setId(number.longValue());
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(new Date(number * ONE_DAY_IN_MILLIS));
		book.setPrice(25D);
		book.setTitle("Title Test" + number);
		return book;
	}
	
	public BookDTO mockDTO(Integer number) {
		BookDTO book = new BookDTO();
		book.setId(number.longValue());
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(new Date(number * ONE_DAY_IN_MILLIS));
		book.setPrice(25D);
		book.setTitle("Title Test" + number);
		return book;
	}
}
