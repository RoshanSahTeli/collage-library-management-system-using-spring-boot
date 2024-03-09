package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.BookingRepo;
import com.example.demo.Repository.bookRepository;
import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Books;
import com.example.demo.entity.student;

@Component
public class userService {
	@Autowired
	private studentRepository srepo;
	
	@Autowired
	private bookRepository brepo;
	
	@Autowired
	private BookingRepo borepo;
	
	public student findByEmail(String email) {
		student s=srepo.findByEmail(email);
		return s;
	}
	public List<BookCountDTO> countBynameandStatus(String category){
		return brepo.findAvailableBooksByNameAndCategory(category);
	}
	
	public List<Books> findByNameAndStatus(String name,String status){
		return brepo.findByBnameAndStatus(name, status);
	}
	
	public Books booked(String bid,String username,LocalDate date,int uid) {
		
		Booking b=new Booking();
		b.setBid(bid);
		b.setUsername(username);
		b.setDate(date);
		b.setUser_id(uid);
		borepo.save(b);
		return  brepo.findById(bid).get();
		
		
	}
	
	public List<Booking>bookings(String username){
		return borepo.findByUsername(username);
	}
	
	public Booking findBookingbyID(int id) {
		return borepo.findById(id).get();
	}
	
	
	public void cancel_booking(int booking_id) {
		borepo.deleteById(booking_id);
		
	}
	
	public List<Books> findAllUser(String status){
		return brepo.findByStatus(status);
	}
	

}
