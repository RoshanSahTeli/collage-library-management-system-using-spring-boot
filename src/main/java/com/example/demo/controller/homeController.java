package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
	 @GetMapping("/login_form")
	 public String form() {
		 return "login_form";
	 }

}
