package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.appRepository;
import com.example.demo.Repository.bookRepository;
import com.example.demo.Repository.issueRepo;
import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.Admins;
import com.example.demo.entity.Books;
import com.example.demo.entity.student;

import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Component
public class appService {
	@Autowired
	private appRepository repo;
	
	@Autowired
	private bookRepository brepo;
	
	@Autowired
	private studentRepository srepo;
	
	@Autowired
	private issueRepo irepo;
	
	
	
	public void add_book(String id,String name,String faculty) {
		Books b=new Books();
		b.setId(id);
		b.setFaculty(faculty);
		b.setName(name);
		b.setDate(LocalDate.now());
		b.setStatus("Available");
		brepo.save(b);
	}
	
	public void issue(String bid,String bname,int sid,String sname,int semester,String faculty) {
		issue i=new issue();
		
		List<Books>bList=brepo.bList(bid);
		student s=new student();
		for(Books book:bList) {
			book.setDate(book.getDate());
			book.setFaculty(book.getFaculty());
			book.setId(bid);
			book.setName(bname);
			book.setStudent(s);
			s.getBookList().add(book);
		}
		s.setSid(sid);
		s.setBookList(bList);
		srepo.save(s);

		
		i.setBid(bid);
		i.setBname(bname);
		i.setSid(sid);
		i.setSname(sname);
		i.setSemester(semester);
		i.setFaculty(faculty);
		i.setIssue_date(LocalDate.now());
		irepo.save(i);
		
		
	}
	
	public List<Books> check_book(String bid,String status){
		List<Books> l=brepo.forIssue(bid,"Available");
		return l;
	}
	
	public Optional<student> check_student(int sid){
		Optional<student> s=srepo.findById(sid);
		return s;
	}
	
	@Transactional
	public void setStatus(String id,String status) {
		brepo.setStatus(id,status);
	}
	
	public void save_student(int sid,String sname,int semester,String faculty) {
		student s=new student();
		s.setSid(sid);
		s.setName(sname);
		s.setSemester(semester);
		s.setFaculty(faculty);
		
		srepo.save(s);
	}
	
	public long countBook(String name,String status) {
		
		long count=brepo.countBook(name,status);
		return count;
	}
	
	
	public Optional<com.example.demo.entity.issue> searchIssuedBook(String bid){
		Optional<com.example.demo.entity.issue>list=irepo.findById(bid);
		return list;
	}
	
	public Optional<Books> searchAvailableBook(String bid){
		Optional<Books>list=brepo.findById(bid);
		return list;
	}
	
	public List<Books>findByNameAndStatus(String bname,String status){
		List<Books>list=brepo.findByNameAndStatus(bname,status);
		return list;
	}
	public Books update_value(String id) {
		Books b=brepo.findById(id).get();
		return b;
	}
	
	@Transactional
	public void update_save(String bid,String bname,String faculty) {
		Books b=new Books();
		b.setFaculty(faculty);
		//b.setId(bid);
		b.setName(bname);
		brepo.update_save(bid, bname, faculty);
	}
	
	public void bdelete(String bid) {
		brepo.deleteById(bid);
	}
	
	public void idelete(String bid) {
		irepo.deleteById(bid);
	}
	public Books find(String bid) {
		Books b=brepo.findById(bid).get();
		return b;
	}
}
