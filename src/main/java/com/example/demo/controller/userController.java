package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.student;
import com.example.demo.service.userService;

@Controller
@RequestMapping("/User")
public class userController {
	@Autowired
	private userService service;
	
	
	@GetMapping("/profile")
	public String form(Principal principal) {
		
		student s=service.findByEmail(principal.getName());
		String status=s.getStatus();
		if(status.equals("Verfied")) {
			return "profile";
		}
		else {
			return "unverified";
		}
		
	}

}
