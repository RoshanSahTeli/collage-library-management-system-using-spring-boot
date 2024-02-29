package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class Books {
	
	@Id
	private String bid;
	
	private String bname;
	
	private String Author;
	
	private String publication;
	
	public LocalDateTime date;
	
	private String Category;
	 
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "sid")
	private student student;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public LocalDateTime getAdd_date() {
		return date;
	}

	public void setAdd_date(LocalDateTime add_date) {
		this.date = add_date;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public student getStudent() {
		return student;
	}

	public void setStudent(student student) {
		this.student = student;
	}

	public Books(String bid, String bname, String author, String publication, LocalDateTime add_date, String Category,
			String status, com.example.demo.entity.student student) {
		super();
		this.bid = bid;
		this.bname = bname;
		Author = author;
		this.publication = publication;
		this.date = add_date;
		this.Category = Category;
		this.status = status;
		this.student = student;
	}

	public Books() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	

	
}
