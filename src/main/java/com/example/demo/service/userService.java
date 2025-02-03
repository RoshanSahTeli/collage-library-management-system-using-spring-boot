package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.BookingRepo;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.bookRepository;
import com.example.demo.Repository.issueRepo;
import com.example.demo.Repository.ratingRepo;
import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Books;
import com.example.demo.entity.Rating;
import com.example.demo.entity.category;
import com.example.demo.entity.issue;
import com.example.demo.entity.student;

@Component
public class userService {
	@Autowired
	private studentRepository srepo;
	
	@Autowired
	private bookRepository brepo;
	
	@Autowired
	private BookingRepo borepo;
	@Autowired
	private issueRepo irepo;
	@Autowired
	private CategoryRepo crepo;
	
	@Autowired
	private ratingRepo rrepo;
	
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
	
	public Books booked(String bid,String username,String bname,LocalDate date,int uid) {
		
		Booking b=new Booking();
		b.setBid(bid);
		b.setUsername(username);
		b.setDate(date);
		b.setUser_id(uid);
		b.setBname(bname);
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
	
	public List<issue> findIssued(int sid){
		return irepo.findIssued(sid);
	}
	public List<category>findlastadded(){
		return crepo.findFirst3ByOrderByNameDesc();
	}
	
	public Books findBookById(String bid) {
		return brepo.findById(bid).get();
	}
	
	public void save_rating(String bid,String email,Rating rating) {
		Rating r= new Rating();
		r.setBook(findBookById(bid));
		r.setReview(rating.getReview());
		r.setRatingValue(rating.getRatingValue());
		r.setStudent(findByEmail(email));
		rrepo.save(r);
	}

}
