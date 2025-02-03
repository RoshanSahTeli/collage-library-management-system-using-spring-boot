package com.example.demo.entity;

public class BookCountDTO {
	
	public String book_name;
	
	private long available;
	
	private long issued;
	
	private long booked;
	
	private double rating;
	
	
	

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	
	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public long getAvailable() {
		return available;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

	public long getIssued() {
		return issued;
	}

	public void setIssued(long issued) {
		this.issued = issued;
	}

	public long getBooked() {
		return booked;
	}

	public void setBooked(long booked) {
		this.booked = booked;
	}

	

	public BookCountDTO(String book_name, long available, long issued, long booked, double rating) {
		super();
		this.book_name = book_name;
		this.available = available;
		this.issued = issued;
		this.booked = booked;
		
		this.rating = rating;
	}
	
	public BookCountDTO(String book_name,long available,double rating) {
		this.book_name=book_name;
		this.available=available;
		this.rating=rating;
	}

	public BookCountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
