package com.library.entity;

import jakarta.persistence.*;
//import javax.persistence.*;


@Entity
@Table(name = "user_books")
public class UserBooks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userID;

	@Column(name = "books_id")
	private Long booksID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getBooksID() {
		return booksID;
	}

	public void setBooksID(Long booksID) {
		this.booksID = booksID;
	}


}
