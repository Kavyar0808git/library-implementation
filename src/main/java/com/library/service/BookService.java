package com.library.service;

import java.util.List;

import com.library.entity.Book;
import com.library.entity.User;
import com.library.entity.UserBooks;

public interface BookService {
	
	List<Book> findAllAvailableBooks();
	
	List<Book> findAll();
	
	User findUserByName(String name);
	
	void saveBook(Book book);
	
	void saveUserBooks(UserBooks userBooks);

	List<UserBooks> findAllBorrowedBooksByUser(Long loggedInUserID);
	
	String findAllUserNameByBorrowedBook(Long bookID);


	Book findBookByID(Long booksID);

	void updateBookCopies(Long booksId);
	
	void updateBookCopiesAdd(Long booksId);


	void deleteUserBook(long id);

	UserBooks findUserBook(Long bookID, Long userID);
	


}
