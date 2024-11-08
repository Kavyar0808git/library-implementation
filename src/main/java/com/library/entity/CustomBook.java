package com.library.entity;

public class CustomBook {

	private Book book;
	private String userNames;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public CustomBook(Book book, String userNames) {
		super();
		this.book = book;
		this.userNames = userNames;
	} 
	
	
}
