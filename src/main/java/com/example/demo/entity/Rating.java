package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Rating {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ratingId;
	
	@ManyToOne
	@JoinColumn(name = "sid")
	private student student;
	
	@ManyToOne
	@JoinColumn(name = "bid")
	private Books book;
	
	@Column(name="rating value")
	//@Min(2)
	@Max(10)
	private int ratingValue;
	
	private String review;

	public int getRatingId() {
		return ratingId;
	}

	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}

	public student getStudent() {
		return student;
	}

	public void setStudent(student student) {
		this.student = student;
	}

	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

	public int getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Rating(int ratingId, com.example.demo.entity.student student, Books book, int ratingValue, String review) {
		super();
		this.ratingId = ratingId;
		this.student = student;
		this.book = book;
		this.ratingValue = ratingValue;
		this.review = review;
	}

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
