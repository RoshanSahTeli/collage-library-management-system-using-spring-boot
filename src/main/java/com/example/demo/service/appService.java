package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.bookRepository;
import com.example.demo.Repository.issueRepo;
import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.Books;
import com.example.demo.entity.issue;
import com.example.demo.entity.student;

import jakarta.transaction.Transactional;

@Component
public class appService {
	@Autowired 
	private JavaMailSender javaMailSender;
	
	
	
	@Autowired
	private bookRepository brepo;
	
	@Autowired
	private studentRepository srepo;
	
	@Autowired
	private issueRepo irepo;
	
	
	
	public void add_book(String name,String author,String publication,String Category) {
		Books b=new Books();
		
		Books boo=brepo.findFirstByBnameOrderByDateDesc(name);
		if(boo==null) {
			String id=name+1;
			b.setBid(id);
		}else {
			
		
		String bname=boo.getBid();
		int name_length=name.length();
		System.out.println(bname);
		int num=Integer.parseInt(bname.substring(name_length))+1;
		System.out.println(num);
		String id=name+num;
		b.setBid(id);
		}		
		
		b.setAdd_date(LocalDateTime.now());
		b.setAuthor(author);
		b.setPublication(publication);
		b.setBname(name);
		b.setStatus("Available");
		b.setCategory(Category);
		brepo.save(b);
	}
	
	public void issue(String bid,String bname,int sid,String sname,String category) {
		issue i=new issue();
		
		student ss=srepo.findById(sid).get();
		List<Books>bList=brepo.bList(bid);
		student s=new student();
		for(Books book:bList) {
			book.setAdd_date(book.getAdd_date());
			book.setCategory(book.getCategory());
			book.setBid(bid);
			book.setBname(bname);
			book.setAuthor(book.getAuthor());
			book.setPublication(book.getPublication());
			book.setStudent(s);
			s.getBookList().add(book);
		}
		s.setSid(sid);
		s.setEmail(ss.getEmail());
		s.setPassword(ss.getPassword());
		s.setPhone(ss.getPhone());
		s.setRole(ss.getRole());
		s.setSid(ss.getSid());
		s.setStatus(ss.getStatus());
		s.setUsername(ss.getUsername());
		s.setBookList(bList);
		srepo.save(s);

		
		i.setBid(bid);
		i.setBname(bname);
		i.setSid(sid);
		i.setSname(sname);
		i.setCategory(category);
		i.setIssue_date(LocalDateTime.now());
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
	
//	public void save_student(int sid,String sname,int semester,String faculty) {
//		student s=new student();
//		s.setSid(sid);
//		s.setName(sname);
//		s.setSemester(semester);
//		s.setFaculty(faculty);
//		srepo.save(s);
//	}
	
	public long countBook(String name,String status) {
		
		long count=brepo.countBook(name,status);
		return count;
	}
	
	
	public List<com.example.demo.entity.issue> searchIssuedBook(String bid,String name){
		List<com.example.demo.entity.issue>list=irepo.findByIdOrName(bid, name);
		return list;
	}
	
	public List<Books> searchAvailableBook(String bid,String name,String status){
		List<Books>list=brepo.findByIdOrName(bid,name,status);
		return list;
	}
	
	public List<Books>findByNameAndStatus(String bname,String status){
		List<Books>list=brepo.findByBnameAndStatus(bname,status);
		return list;
	}
	public Books update_value(String id) {
		Books b=brepo.findById(id).get();
		return b;
	}
	
	@Transactional
	public void update_save(String bid,String bname,String Category,String author,String publication) {
		Books bo=brepo.findById(bid).get();
		
		Books b=new Books();
		b.setCategory(Category);
		b.setBid(bid);
		b.setBname(bname);
		b.setAuthor(author);
		b.setPublication(publication);
		b.setAdd_date(bo.getAdd_date());
		b.setStatus(bo.getStatus());
		brepo.save(b);
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
	public List<student> request(String status){
		List<student>list=srepo.findByStatus(status);
		return list;
	}
	
	@Transactional
	public void verify(String status,int id) {
		srepo.updateVerification(status, id);
	}
	
	public student findStudent(int id) {
		student s=srepo.findById(id).get();
		return s;
	}
	
	public void mail(String to,String body,String subject) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("Roshanshah920@gmail.com");
		message.setTo(to);
		message.setText(body);
		message.setSubject(subject);
		javaMailSender.send(message);

	}
	public void reject(int id) {
		srepo.deleteById(id);
	}
	
	public Books issue_search(String bid) {
		Books b=brepo.findById(bid).get();
		return b;}
	
//		public Books findLatest() {
//			return brepo.findTopByOrderByDateDesc();
//		}
	public student findAdmin(String username) {
		return srepo.findByEmail(username);
	}
	public List<Books> findAllBooks() {
		return brepo.findAll();
	}
}
