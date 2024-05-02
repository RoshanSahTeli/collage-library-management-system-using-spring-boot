package com.example.demo.entity;

public class BookCountDTO {
	
	public String book_name;
	
	private long available;
	
	private long issued;
	
	private long booked;
	
	private String status;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public BookCountDTO(String book_name, long available, long issued, long booked) {
		super();
		this.book_name = book_name;
		this.available = available;
		this.issued = issued;
		this.booked = booked;
		
	}
	
	public BookCountDTO(String book_name,long count) {
		super();
		this.book_name=book_name;
		this.available=count;
	}

	public BookCountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
