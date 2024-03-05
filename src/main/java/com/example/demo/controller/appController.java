package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Books;
import com.example.demo.entity.student;
import com.example.demo.service.appService;
import com.example.demo.service.homeService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
	public void add_book(@RequestParam("bname")
	String name, @RequestParam("author")String author, @RequestParam("publication")String publication ,@RequestParam("category")String category,
	HttpServletResponse response) throws IOException {
		service.add_book( name,author,publication, category);
		response.sendRedirect("/Admin/findAll");
	}
	
	@GetMapping("/home")
	public String home( Principal principal,Model model) {
		List<student> list=  service.request("Unverified");
		int count=list.size();
		 model.addAttribute("list",list);
		 model.addAttribute("count", count);
		student s=hservice.userStatus(principal.getName());
		model.addAttribute("email", principal.getName());
		student ss=service.findAdmin(principal.getName());
		model.addAttribute("username",ss.getUsername());
		String status=s.getStatus();
		if(status.equals("Verified")) {
			return "index";
		}
		else {
			return "unverified";
		}
		
		
	}
	
	@GetMapping("/findAll")
		public String findAll(Model model) {
			List<Books>list=service.findAllBooks();
			for(Books b:list) {
				if(b.getStatus().equals("Available")) {
					String url="/Admin/issue_search";
					System.out.println(b.getBid());
					model.addAttribute("link", url);
					model.addAttribute("link_name", "Issue");
				}
				else {
					String url="/Admin/issue_details";
					model.addAttribute("link", url);
					model.addAttribute("link_name", "Details");
				}
			}
			model.addAttribute("all", list);
			return "allBooks";
		}
	
	
	@GetMapping("/issue_form")
	public String issue_form() {
		return "issue_form";
	}
	
	@PostMapping("/issue")
	public String issue(@RequestParam("bid") String bid,@RequestParam("bname")String bname,
			@RequestParam("sid") int sid,@RequestParam("sname")String sname,Model  model) {
		
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
		service.issue(bid, sid, sname,bname);
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
		
		List<com.example.demo.entity.issue> ilist=service.searchIssuedBook(id);
		List<Books>blist=service.searchAvailableBook(id,name,"Available");
		model.addAttribute("ilist", ilist);
		model.addAttribute("alist", blist);
		
		return "search_issued";
			
		
		

	}
//	@GetMapping("/available_details")
//	public String availableBooks(Model model,HttpSession session){
//		String bname=(String) session.getAttribute("bname");
//		List<Books>list=service.findByNameAndStatus(bname, "Available");
//		model.addAttribute("alist", list);
//		return "book_details";
//	}
//	@GetMapping("/issued_details")
//	public String issuedBooks(Model model,HttpSession session){
//		String bname=(String) session.getAttribute("bname");
//		List<Books>list=service.findByNameAndStatus(bname, "issued");
//		model.addAttribute("alist", list);
//		return "book_details";
//	}
	
	@GetMapping("/update")
	public String update_form(@RequestParam("id")String id,  Model model) {
		
		Books b=service.update_value(id);
		model.addAttribute("values", b);
		
		return "updateForm";
	}
	
	@PostMapping("/update_save")
	public void update_save(@RequestParam("bid")String bid,@RequestParam("bname")String bname,
			@RequestParam("category")String category,@RequestParam("author")String author,
			@RequestParam("publication")String publication,HttpServletResponse response) {
			
		service.update_save(bid, bname, category,author,publication);
		try {
			response.sendRedirect("/Admin/findAll");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@GetMapping("/delete")
	public void  delete( @RequestParam("id") String bid, HttpServletResponse response) throws IOException {
		
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
		response.sendRedirect("/Admin/findAll");
	}
	
	@GetMapping("/show_requests")
	public String requests(Model model) {
		 List<student> list=  service.request("Unverified");
		 int count=list.size();
		 model.addAttribute("list",list);
		 model.addAttribute("count", count);
		 System.out.println(count);
		return "request";
	}
	@GetMapping("/verify")
	public void verify( @RequestParam("id")int sid,HttpServletResponse response) throws IOException {
		service.verify("Verfied", sid);
		student s=service.findStudent(sid);
		
		//service.mail(s.getEmail(),"You are verified user of Library","verficication");
		response.sendRedirect("/Admin/show_requests");
		
		
	}
	
	@GetMapping("/reject")
	public String reject(@RequestParam("id")int sid) {
		student s=service.findStudent(sid);
		//service.mail(s.getEmail(), "Account creation failed", "rejected");
		service.reject(sid);
	
		return "login_success";
	}
	
	
//	@GetMapping("/findLatest")
//	public String findLatest() {
//		Books b=service.findLatest();
//		System.out.println(b.getBid());
//		return "latest";
//	}
	
	@GetMapping("/students")
	public String view_student(Model model) {
		List<student> slist=service.find_student("USER", "Verified");
		model.addAttribute("slist", slist);
		return "student";
	}
	
	@GetMapping("/stuForm")
	 public String addstu(HttpSession session) {
		 session.setAttribute("role","USER");
		 return "add_student_form";
	 }
	
	 @PostMapping("/stuSave")
	 public String stuSave(@Valid @ModelAttribute(value="student") student stu, BindingResult result
			 ,HttpSession session) {
		 	
			String role=(String) session.getAttribute("role");
			service.stuSave(stu,role);
		 
		 return "add_student_form";
		 
	 }
	 
	 @GetMapping("/admin_form")
	 public String addAdminForm(HttpSession session) {
		 session.setAttribute("role", "ADMIN");
		 return "add_admin_form";
	 }
	 
	 @GetMapping("/findAdmins")
	 public String findAdmins(Model model) {
		 List<student> slist=service.find_student("ADMIN","Verified");
		 model.addAttribute("slist", slist);
		 return "student";
	 }
	 
	 

	 @GetMapping("/details")
	 public String book_details(@RequestParam("name")String bname,Model model) {
		 List<Books>list=service.findByName(bname);
		 
		 model.addAttribute("blist", list);
		 return "book_details";
		 
	 }
	 @GetMapping("/issue_search")
		public String issue_search(@RequestParam("id")String bid,Model model) {
			Books b=service.issue_search(bid);
			model.addAttribute("issue", b);
			return "issue";
		}
	 
	 @GetMapping("/fiction")
	 public String fiction(Model model) {		 
		List<BookCountDTO>bcd=service.list("fiction");
		model.addAttribute("groupby", bcd);
		 return "book_category";
	 }
	 
	 @GetMapping("/horror")
	 public String horror(Model model) {		 
		List<BookCountDTO>bcd=service.list("horror");
		model.addAttribute("groupby", bcd);
		 return "book_category";
	 }
	 @GetMapping("/poetry")
	 public String poetry(Model model) {		 
		List<BookCountDTO>bcd=service.list("poetry");
		model.addAttribute("groupby", bcd);
		 return "book_category";
	 }
	 @GetMapping("/drama")
	 public String drama(Model model) {		 
		List<BookCountDTO>bcd=service.list("drama");
		model.addAttribute("groupby", bcd);
		 return "book_category";
	 }
	 @GetMapping("/history")
	 public String history(Model model) {		 
		List<BookCountDTO>bcd=service.list("history");
		model.addAttribute("groupby", bcd);
		 return "book_category";
	 }
	 
	 @GetMapping("/issue_details")
	 public String issue_details(@RequestParam("id")String bid,Model model) {
		 com.example.demo.entity.issue i=service.issue_details(bid);
		 model.addAttribute("issue_details", i);
		 return "issue_details";
	 }
}
