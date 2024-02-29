package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.Books;
import com.example.demo.entity.student;

@Component
public class homeService {
	
	@Autowired
	private studentRepository repo;
	
	public void save_signup(student stu) {
		student s=new student();
		s.setEmail(stu.getEmail());
		s.setSid(stu.getSid());
		s.setPassword(new BCryptPasswordEncoder().encode(stu.getPassword()));
		s.setPhone(stu.getPhone());
		s.setUsername(stu.getUsername());
		s.setAddress(stu.getAddress());
		s.setStatus("Unverified");
		s.setRole("USER");
		repo.save(s);
		
	}
	
	public student userStatus(String email) {
		 student s= repo.findByEmail(email);
		 return s;
	}

}
