package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	public String category;
	 
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "sid")
	private student student;
	
	@OneToOne(mappedBy = "books", cascade = CascadeType.ALL,orphanRemoval = true)
	private issue issue;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Rating> ratingList;
	
	

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	

	public issue getIssue() {
		return issue;
	}

	public void setIssue(issue issue) {
		this.issue = issue;
	}

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
		return category;
	}

	public void setCategory(String Category) {
		this.category = Category;
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

	

	

	public Books(String bid, String bname, String author, String publication, LocalDateTime date, String category,
			String status, com.example.demo.entity.student student, com.example.demo.entity.issue issue) {
		super();
		this.bid = bid;
		this.bname = bname;
		Author = author;
		this.publication = publication;
		this.date = date;
		this.category = category;
		this.status = status;
		this.student = student;
		this.issue = issue;
	}

	public Books() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	

	
}
