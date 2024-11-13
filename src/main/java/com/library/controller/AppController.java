package com.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.entity.Book;
import com.library.entity.CustomBook;
import com.library.entity.User;
import com.library.entity.UserBooks;
import com.library.service.BookService;

@Controller
public class AppController {

	private User loggedInUserData;

	@Autowired
	private BookService bookService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		return "index";
	}

	//Logic to display list of available books on library click for user or admin user. 
	//This will also give information about how many books can user borrow.
	@GetMapping("/library")
	public String viewLibrary(Model model) {
		List<Book> listBooks = bookService.findAllAvailableBooks();
		model.addAttribute("listBooks", listBooks);

		List<UserBooks> listUserBooks = bookService.findAllBorrowedBooksByUser(loggedInUserData.getId());

		// always allow only 2 books to be borrowed at any point of time.
		switch (listUserBooks.size()) {
		case 0:
			model.addAttribute("size", 2);
			break;
		case 1:
			model.addAttribute("size", 1);
			break;
		case 2:
			model.addAttribute("size", 0);
			break;
		default:
			model.addAttribute("size", 2);
		}

		return "library";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}

	//Admin user can click on Add new book and its corresponding page is displayed to register new Book.
	@GetMapping("/addNewBook")
	public String addNewBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "addBook";
	}

	//Once we click on login button, if its admin user then they will be able to add new books, view library and borrow books, view all the books with user names inventory and 
	//if its normal user then can view the library and borrow books.
	@PostMapping("/openLibrary")
	public String openLibrary(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, Model model) {
		User loggedInUser = bookService.findUserByName(user.getUserName());
		String isAdmin = loggedInUser != null ? loggedInUser.getIsAdmin() : "N";
		loggedInUserData = loggedInUser;

		if (isAdmin.equals("N")) {
			return "openLibrary";
		} else {
			// Admin login
			List<Book> listBooks = bookService.findAll();

			List<CustomBook> customBook = new ArrayList<CustomBook>();
			listBooks.forEach(listBook -> {
				customBook.add(new CustomBook(listBook, bookService.findAllUserNameByBorrowedBook(listBook.getId())));
			});
			model.addAttribute("listBooks", customBook);
			return "adminLibrary";
		}
	}

	//This method to store the new books in DB for admin user.
	@PostMapping("/saveBook")
	public String saveBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes, Model model) {
		book.setAvailableCopies(book.getTotalCopies());
		bookService.saveBook(book);

		List<Book> listBooks = bookService.findAll();
		List<CustomBook> customBook = new ArrayList<CustomBook>();
		listBooks.forEach(listBook -> {
			customBook.add(new CustomBook(listBook, bookService.findAllUserNameByBorrowedBook(listBook.getId())));
		});
		model.addAttribute("listBooks", customBook);
		return "adminLibrary";
	}

	//This method will store the borrowed books for the user. At a time user can borrow only 2 books. 
	//As per design once the book is borrowed, the available copies count will reduce in book table.
	//After submit display borrowed list.
	@PostMapping("/saveBorrowedList")
	public String saveBorrowedList(@RequestParam("select") Optional<List<Long>> booksIDList, Model model) {
		if (loggedInUserData != null) {
			List<UserBooks> listUserBooks = bookService.findAllBorrowedBooksByUser(loggedInUserData.getId());

			if (listUserBooks.size() < 2) {
				booksIDList.stream().forEach(booksId -> {
					UserBooks userBooks = new UserBooks();
					userBooks.setBooksID(booksId.get(0));
					userBooks.setUserID(loggedInUserData.getId());
					bookService.saveUserBooks(userBooks);
					listUserBooks.add(userBooks);

					// reduce the available copies
					bookService.updateBookCopies(booksId.get(0));
				});
			}

			List<Book> listBooks = new ArrayList<Book>();
			listUserBooks.stream().forEach(userBook -> {
				try {
					listBooks.add(bookService.findBookByID(userBook.getBooksID()));
				} catch (Exception ex) {
					System.out.println("No book found for ID: " + userBook.getBooksID());
				}
			});

			model.addAttribute("listBooks", listBooks);

		}
		return "borrowedList";
	}

	//Display borrowed list for the given logged in user.
	@GetMapping("/borrowedList")
	public String borrowedList(Model model) {
		List<UserBooks> listUserBooks = bookService.findAllBorrowedBooksByUser(loggedInUserData.getId());

		List<Book> listBooks = new ArrayList<Book>();
		listUserBooks.stream().forEach(userBook -> {
			try {
				listBooks.add(bookService.findBookByID(userBook.getBooksID()));
			} catch (Exception ex) {
				System.out.println("No book found for ID: " + userBook.getBooksID());
			}
		});
		model.addAttribute("listBooks", listBooks);
		return "borrowedList";
	}

	//Return the borrowed book to library for the logged in user and book id. Therefore increase the available copies by 1 number in book table.
	@GetMapping("/returnBook/{id}")
	public String returnBook(@PathVariable(value = "id") long id, Model model) {
		UserBooks userBooks = bookService.findUserBook(id, loggedInUserData.getId());

		bookService.deleteUserBook(userBooks.getId());
		// reduce the available copies
		bookService.updateBookCopiesAdd(id);
		borrowedList(model);
		return "borrowedList";
	}

}
