package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.student;
import com.example.demo.service.homeService;

import jakarta.validation.Valid;

@Controller
public class homeController {
	
	
	@Autowired
	private homeService service;
	
	 @GetMapping("/login_form")
	 public String form() {
		 return "login_form";
	 }
	 
	 @GetMapping("/")
	 public String signup(Model model) {
		 model.addAttribute("student", new student());
		 return "signup";
	 }
	 
	 @PostMapping("/signup_save")
	 public String signup_save(@Valid @ModelAttribute(value="student") student stu, BindingResult result) {
		 
			 System.out.println(result);
			 System.out.println(stu.getEmail());
			 System.out.println(stu.getPassword());
			 service.save_signup(stu);
		 
		 
		 return "login_form";
		 
	 }

}
