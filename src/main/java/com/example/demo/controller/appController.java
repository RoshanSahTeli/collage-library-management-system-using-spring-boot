package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Books;
import com.example.demo.entity.student;
import com.example.demo.service.appService;
import com.example.demo.service.homeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin")
public class appController {
	
	@Autowired
	private appService service;
	
@Autowired
private homeService hservice;

	@GetMapping("/add_book_form")
	public String add_book() {
		return "add_book_form";
	}
	
	@PostMapping("/add_book")
	public String add_book(@RequestParam("book_id") String id,@RequestParam("book_name")
	String name, @RequestParam("author")String author, @RequestParam("publication")String publication ,@RequestParam("book_faculty")String faculty) {
		
		service.add_book(id, name,author,publication, faculty);
		return "add_success";
	}
	
	@GetMapping("/home")
	public String home( Principal principal) {
		student s=hservice.userStatus(principal.getName());
		String status=s.getStatus();
		if(status.equals("Verfied")) {
			return "login_success";
		}
		else {
			return "unverified";
		}
		
		
	}
	
	@GetMapping("/issue_form")
	public String issue_form() {
		return "issue_form";
	}
	
	@PostMapping("/issue")
	public String issue(@RequestParam("bid") String bid,@RequestParam("bname")String bname,
			@RequestParam("sid") int sid,@RequestParam("sname")String sname,@RequestParam("semester")
	int semester, @RequestParam("faculty")String faculty,Model  model) {
		
		List<Books> l=service.check_book(bid,"Available");
		Optional<student> s=service.check_student(sid);
		if(l.isEmpty()){
			model.addAttribute("type", "Book");
			model.addAttribute("id", bid);
			return "not_available";
		}
		
		else if(s.isEmpty()) {
			model.addAttribute("type", "Student");
			model.addAttribute("id", sid);
			return "not_available";
		}
		else {
		service.issue(bid, bname, sid, sname, semester, faculty);
		service.setStatus(bid,"issued");
		return "issue_success";}
	}
	
	@GetMapping("/add_student_form")
	public String add_form() {
		return "add_student_form";
	}
	
//	@PostMapping("/add_student")
//	public String save_studnet(  @RequestParam("sid")int sid, @RequestParam("sname")String name, @RequestParam("semester") int semester,
//			 @RequestParam("faculty") String faculty) {
//		
//		service.save_student(sid,name, semester, faculty);
//		return "add_student_Success";
//	}
	
	@GetMapping("/show_requests")
	public String requests(Model model) {
		 List<student> list=  service.request("Unverified");
		 model.addAttribute("list",list);
		return "request";
	}
	
	@GetMapping ("/csit")
	public String csit() {
		return "csit";
	}
	
	@GetMapping("/6_sem")
	public String six_sem() {
		return "6_sem";
	}
	
	@GetMapping("/dot_net")
	public String dot_net(Model model,HttpSession session) {
			session.setAttribute("bname",".net");
		 long count_available=service.countBook(".net","Available");
		 model.addAttribute("count", count_available);
		 long count_issued=service.countBook(".net", "issued");
		 model.addAttribute("count_issued", count_issued);
		return "details";
	}
	@GetMapping("/java")
	public String java(Model model,HttpSession session) {
			session.setAttribute("bname","java");
		 long count_available=service.countBook("java","Available");
		 model.addAttribute("count", count_available);
		 long count_issued=service.countBook("java", "issued");
		 model.addAttribute("count_issued", count_issued);
		return "details";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("search")String id,@RequestParam("search")String name,   Model model) {
		
		List<com.example.demo.entity.issue> ilist=service.searchIssuedBook(id,name);
		List<Books>blist=service.searchAvailableBook(id,name,"Available");
		model.addAttribute("ilist", ilist);
		model.addAttribute("alist", blist);
		
		return "search_issued";
			
		
		

	}
	@GetMapping("/available_details")
	public String availableBooks(Model model,HttpSession session){
		String bname=(String) session.getAttribute("bname");
		List<Books>list=service.findByNameAndStatus(bname, "Available");
		model.addAttribute("alist", list);
		return "book_details";
	}
	@GetMapping("/issued_details")
	public String issuedBooks(Model model,HttpSession session){
		String bname=(String) session.getAttribute("bname");
		List<Books>list=service.findByNameAndStatus(bname, "issued");
		model.addAttribute("alist", list);
		return "book_details";
	}
	
	@GetMapping("/update")
	public String update_form(@RequestParam("id")String id,  Model model) {
		
		Books b=service.update_value(id);
		model.addAttribute("values", b);
		
		return "update_form";
	}
	
	@PostMapping("/update_save")
	public String update_save(@RequestParam("bid")String bid,@RequestParam("bname")String bname,
			@RequestParam("faculty")String faculty,@RequestParam("author")String author,
			@RequestParam("publication")String publication) {
			
		service.update_save(bid, bname, faculty,author,publication);
		return "updated";
	}
	
	@GetMapping("/delete")
	public String  delete( @RequestParam("id") String bid) {
		
		Books b=service.find(bid);
		String status=b.getStatus();
		String s="issued";
		if(status.equals(s)) {
			service.idelete(bid);
			service.setStatus(bid, "Available");
		}
		else {
			service.bdelete(bid);
		}
		return "login_success";
	}
	@GetMapping("/verify")
	public String verify( @RequestParam("id")int sid) {
		service.verify("Verfied", sid);
		student s=service.findStudent(sid);
		
		//service.mail(s.getEmail(),"You are verified user of Library","verficication");
	
		return "login_success";
		
	}
	
	@GetMapping("/reject")
	public String reject(@RequestParam("id")int sid) {
		student s=service.findStudent(sid);
		//service.mail(s.getEmail(), "Account creation failed", "rejected");
		service.reject(sid);
	
		return "login_success";
	}
	

	

}
