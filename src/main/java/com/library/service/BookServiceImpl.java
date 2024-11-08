package com.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.entity.User;
import com.library.entity.UserBooks;
import com.library.repository.BookRepository;
import com.library.repository.UserBooksRepository;
import com.library.repository.UserRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private UserBooksRepository userBooksRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<Book> findAllAvailableBooks() {
		return bookRepo.findAllAvailableBooks();
	}

	@Override
	public User findUserByName(String name) {
		return userRepo.findUserByName(name);
	}

	@Override
	public void saveBook(Book book) {
		bookRepo.save(book);
	}

	@Override
	public List<Book> findAll() {
		return bookRepo.findAll();
	}

	@Override
	public void saveUserBooks(UserBooks userBooks) {
		userBooksRepo.save(userBooks);
	}

	@Override
	public List<UserBooks> findAllBorrowedBooksByUser(Long loggedInUserID) {
		return userBooksRepo.findAllBorrowedBooksByUser(loggedInUserID);
	}

	@Override
	public Book findBookByID(Long booksID) {
		return (bookRepo.findById(booksID)).get();
	}

	@Override
	public void updateBookCopies(Long booksId) {
		Book book = bookRepo.findById(booksId).get();
		book.setAvailableCopies((book.getAvailableCopies()) - 1);
		bookRepo.save(book);
	}

	@Override
	public void updateBookCopiesAdd(Long booksId) {
		Book book = bookRepo.findById(booksId).get();
		book.setAvailableCopies((book.getAvailableCopies()) + 1);
		bookRepo.save(book);
	}

	@Override
	public void deleteUserBook(long id) {
		userBooksRepo.deleteById(id);
	}

	@Override
	public UserBooks findUserBook(Long bookID, Long userID) {
		UserBooks userBook = userBooksRepo.findUserBook(bookID, userID);
		return userBook;
	}

	@Override
	public String findAllUserNameByBorrowedBook(Long bookID) {
		List<Long> userID = userBooksRepo.findAllBorrowedBooksByBook(bookID);
		String userNameStr = null;

		if (userID.size() > 0) {
			StringBuffer userName = new StringBuffer();
			userID.forEach(id -> {
				userName.append(userRepo.findUserNameByID(id));
				userName.append(",");
			});
			userNameStr = userName != null ? userName.toString() : null;

		}

		return userNameStr;

	}

}
