package com.example.demo.entity;

public class BookCountDTO {
	
	public String book_name;
	
	private long book_count;
	
	public String status;
	
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

	public long getBook_count() {
		return book_count;
	}

	public void setBook_count(long book_count) {
		this.book_count = book_count;
	}

	

	public BookCountDTO(String book_name,long book_count) {
		super();
		this.book_name = book_name;
		this.book_count = book_count;
		
	}

	public BookCountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BookCountDTO [book_name=" + book_name + ", book_count=" + book_count + "]";
	}
	
	

}
