package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{


	@Query("Select b from Book b where b.availableCopies >0 ")
	public List<Book> findAllAvailableBooks();	
	
	
}
