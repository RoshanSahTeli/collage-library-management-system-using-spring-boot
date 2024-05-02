package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.student;
import com.example.demo.service.homeService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class homeController {
	
	
	@Autowired
	private homeService service;
	
	 @GetMapping("/login_form")
	 public String form() {
		 	
		 return "login_form";
	 }
	 
	 @GetMapping("/signup")
	 public String signup(Model model) {
		 model.addAttribute("student", new student());
		 model.addAttribute("student", new student());
		 return "signup";
	 }
	 
	 @PostMapping("/signup_save")
	 public String signup_save(@Valid @ModelAttribute(value="student") student stu, BindingResult result,
			 @RequestParam("image")  MultipartFile file) throws IllegalStateException, IOException {
		 		
			if(result.hasErrors()) {
				System.out.println(result);
				return "signup";
			}
			else {
			 service.save_signup(stu,file);
			 return "login_form";}
		 
		 
		 
	 }
	 
	 

}
