package com.jhonn.santt4na_rest.repository;

import com.jhonn.santt4na_rest.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
}
