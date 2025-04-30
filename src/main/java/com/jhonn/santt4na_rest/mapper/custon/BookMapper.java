package com.jhonn.santt4na_rest.mapper.custon;

import com.jhonn.santt4na_rest.dataDTO.v1.BookDTO;
import com.jhonn.santt4na_rest.model.Books;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class BookMapper {
	
	public BookDTO convertEntityToDTO(Books book){
		BookDTO dto = new BookDTO();
		dto.setId(book.getId());
		dto.setAuthor(book.getAuthor());
		dto.setPrice(book.getPrice());
		dto.setTitle(book.getTitle());
		dto.setLaunch_date(new Date());
		return dto;
	}
	
	public Books convertDTOToEntity(BookDTO bookDTO){
		Books entity = new Books();
		entity.setId(bookDTO.getId());
		entity.setAuthor(bookDTO.getAuthor());
		entity.setPrice(bookDTO.getPrice());
		entity.setTitle(bookDTO.getTitle());
		entity.setLaunch_date(bookDTO.getLaunch_date());
		return entity;
	}
	
}
