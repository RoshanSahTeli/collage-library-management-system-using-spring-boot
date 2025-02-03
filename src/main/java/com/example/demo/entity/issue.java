package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "issue")
public class issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long issueID;

	@ManyToOne
	@JoinColumn(name = "sid", nullable = false)
	private student student;
	
	@ManyToOne
	@JoinColumn(name = "bid",nullable = false)
	private Books books;
	
	private LocalDate issue_date;
	
	private LocalDate expiry_date;
	
	@ManyToOne
	@JoinColumn(name = "issuedBy",nullable = false)
	private student issuedBy;

	public long getIssueID() {
		return issueID;
	}

	public void setIssueID(long issueID) {
		this.issueID = issueID;
	}

	public student getStudent() {
		return student;
	}

	public void setStudent(student student) {
		this.student = student;
	}

	public Books getBooks() {
		return books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	public LocalDate getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(LocalDate issue_date) {
		this.issue_date = issue_date;
	}

	public LocalDate getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(LocalDate expiry_date) {
		this.expiry_date = expiry_date;
	}

	public student getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(student issuedBy) {
		this.issuedBy = issuedBy;
	}

	public issue(long issueID, com.example.demo.entity.student student, Books books, LocalDate issue_date,
			LocalDate expiry_date, com.example.demo.entity.student issuedBy) {
		super();
		this.issueID = issueID;
		this.student = student;
		this.books = books;
		this.issue_date = issue_date;
		this.expiry_date = expiry_date;
		this.issuedBy = issuedBy;
	}

	public issue() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
