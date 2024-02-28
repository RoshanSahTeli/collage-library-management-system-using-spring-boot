package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.student;

@Component
public class userService {
	@Autowired
	private studentRepository srepo;
	
	public student findByEmail(String email) {
		student s=srepo.findByEmail(email);
		return s;
	}

}
