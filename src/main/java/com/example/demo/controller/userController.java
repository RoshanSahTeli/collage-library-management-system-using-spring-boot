package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Books;
import com.example.demo.entity.student;
import com.example.demo.service.appService;
import com.example.demo.service.userService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/User")
public class userController {
	@Autowired
	private userService service;
	
	@Autowired
	private appService aservice;
	
	
	@GetMapping("/home")
	public String form(Principal principal,Model model,HttpSession session) {
		
		student s=service.findByEmail(principal.getName());
		String status=s.getStatus();
		if(status.equals("Verified")) {
			List<com.example.demo.entity.category> category_list=aservice.get_categories();
			session.setAttribute("categories", category_list);
			model.addAttribute("categories", category_list);
			model.addAttribute("username",s.getUsername());
			model.addAttribute("email", s.getEmail());
			session.setAttribute("username", s.getUsername());
			session.setAttribute("email", s.getEmail());
			return "profile";
		}
		else {
			return "unverified";
		}
		
	}
	
	@GetMapping("/category/{category_id}")
	 public String fiction(Model model,HttpSession session,@PathVariable("category_id") int id) {
		 com.example.demo.entity.category categories=aservice.findCategoryById(id);
			model.addAttribute("categories", session.getAttribute("categories"));
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("email",session.getAttribute("email") );
		List<BookCountDTO>bcd=service.countBynameandStatus(categories.getName());
		
		model.addAttribute("groupby", bcd);
		 return "category_user";
	 }
	@GetMapping("/details")
	 public String book_details(@RequestParam("name")String bname,Model model,HttpSession session) {
			model.addAttribute("categories",session.getAttribute("categories"));
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("email",session.getAttribute("email") );
		 List<Books>list=service.findByNameAndStatus(bname, "Available");
		 
		 model.addAttribute("blist", list);
		 return "book_details_user";
		 
	 }
//	@GetMapping("/issue_search")
//	public String issue_search(@RequestParam("id")String bid,Model model,HttpSession session) {
//	 	model.addAttribute("list", session.getAttribute("request"));
//		model.addAttribute("count", session.getAttribute("count"));
//		model.addAttribute("categories", session.getAttribute("categories"));
//		model.addAttribute("username", session.getAttribute("username"));
//		model.addAttribute("email",session.getAttribute("email") );
//		Books b=aservice.issue_search(bid);
//		model.addAttribute("issue", b);
//		return "issue";
//	}
//	@GetMapping("/delete")
//	public void  delete( @RequestParam("id") String bid, HttpServletResponse response) throws IOException {
//		
//		Books b=aservice.find(bid);
//		String status=b.getStatus();
//		String s="issued";
//		if(status.equals(s)) {
//			aservice.idelete(bid);
//			aservice.setStatus(bid, "Available");
//		}
//		else {
//			aservice.bdelete(bid);
//		}
//		response.sendRedirect("/Admin/findAll");
//	}
//	@GetMapping("/update")
//	public String update_form(@RequestParam("id")String id,  Model model,HttpSession session) {
//		
//		model.addAttribute("categories", session.getAttribute("categories"));
//		model.addAttribute("username", session.getAttribute("username"));
//		model.addAttribute("email",session.getAttribute("email") );
//		Books b=aservice.update_value(id);
//		model.addAttribute("values", b);
//		
//		return "updateForm";
//	}
	@GetMapping("/booked")
	public void booked( @RequestParam("id") String bid,HttpServletResponse response,HttpSession session,Model model) throws IOException {
		//Books b=service.booked(bid);
		model.addAttribute("categories",session.getAttribute("categories"));
		model.addAttribute("username", session.getAttribute("username"));
		model.addAttribute("email",session.getAttribute("email") );
		String username=(String) session.getAttribute("username");
		student s=aservice.findAdmin((String) session.getAttribute("email"));
		service.booked(bid, username, LocalDate.now(),s.getSid());
		aservice.setStatus(bid, "Booked");
		
		response.sendRedirect("/User/MyBookings");
	}
	
	@GetMapping("/MyBookings")
	public String myBookings(Model model,HttpSession session) {
		model.addAttribute("categories",session.getAttribute("categories"));
		model.addAttribute("username", session.getAttribute("username"));
		model.addAttribute("email",session.getAttribute("email") );
		List<Booking> b=service.bookings((String) session.getAttribute("username"));
		model.addAttribute("bookings", b);
		return "myBooking";
	}
	
	@GetMapping("/cancel")
	public void cancel_booking( @RequestParam("id")  int booking_id,HttpServletResponse response
			,HttpSession session,Model model) throws IOException {
		Booking b=service.findBookingbyID(booking_id);
		aservice.setStatus(b.getBid(), "Available");
		service.cancel_booking(booking_id);
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("username", session.getAttribute("username"));
		model.addAttribute("email",session.getAttribute("email") );
		response.sendRedirect("/User/MyBookings");
		
	}
	@GetMapping("/findAll")
	public String findAll(Model model,HttpSession session) {
		List<Books>list=service.findAllUser("Available");
		model.addAttribute("all", list);
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("username", session.getAttribute("username"));
		model.addAttribute("email",session.getAttribute("email") );
		return "allAvailable";
	}


}
