package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.entity.UserBooks;

public interface UserBooksRepository extends JpaRepository<UserBooks, Long> {

	@Query("Select b from UserBooks b where b.userID = ?1 ")
	List<UserBooks> findAllBorrowedBooksByUser(Long loggedInUserID);

	@Query("Select b from UserBooks b where b.booksID = ?1 and b.userID = ?2 ")
	UserBooks findUserBook(Long bookID, Long userID);
	
	@Query("Select distinct b.userID from UserBooks b where b.booksID = ?1 ")
	List<Long> findAllBorrowedBooksByBook(Long bookID);
	
	

}
