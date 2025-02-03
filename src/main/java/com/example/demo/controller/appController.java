package com.example.demo.controller;

import java.awt.print.Book;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Books;
import com.example.demo.entity.issue;
import com.example.demo.entity.student;
import com.example.demo.service.appService;
import com.example.demo.service.homeService;
import com.opencsv.exceptions.CsvException;

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
	public String add_book(Model model, HttpSession session) {
		List<com.example.demo.entity.category> list = service.category_dropdown();
		model.addAttribute("category", list);
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));

		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
	
		return "add_book_form";
	}

	@PostMapping("/add_book")
	public void add_book(@RequestParam("bname") String name, @RequestParam("author") String author,
			@RequestParam("publication") String publication, @RequestParam("category") String category,
			HttpServletResponse response, Model model, HttpSession session) throws IOException {
		service.add_book(name, author, publication, category);

		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
	
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		
		response.sendRedirect("/Admin/findAll");
	}

	@GetMapping("/home")
	public String home(Principal principal, Model model, HttpSession session) {
		List<student> list = service.request("Unverified");
		List<com.example.demo.entity.category> category_list = service.get_categories();
		session.setAttribute("categories", category_list);
		model.addAttribute("categories", category_list);
		model.addAttribute("category_count", category_list.size());
		session.setAttribute("request", list);
		int size=service.expired().size();
		model.addAttribute("expired_count", size);
		model.addAttribute("issue_count", service.issued_list().size());
		session.setAttribute("expired_count", size);
		session.setAttribute("issue_count", service.issued_list().size());
		model.addAttribute("countBooking", service.countBooking());
		int count = list.size();
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		session.setAttribute("count", count);
		student s = hservice.userStatus(principal.getName());
		model.addAttribute("av", service.findBooksByStatus("Available").size());
		model.addAttribute("is", service.findBooksByStatus("issued").size());
		model.addAttribute("email", principal.getName());
		student ss = service.findAdmin(principal.getName());
		model.addAttribute("user", ss);
		session.setAttribute("user", ss);
		List<Booking> blist = service.bookings();
		model.addAttribute("blist", blist);
		model.addAttribute("stu", service.findbyRole("USER", "Verified"));
		model.addAttribute("scount", service.findbyRole("USER", "Verified").size());
		model.addAttribute("acount", service.findbyRole("ADMIN", "Verified").size());
		model.addAttribute("adm", service.findbyRole("ADMIN", "Verified"));

		String status = s.getStatus();
		if (status.equals("Verified")) {
			return "index";
		} else {
			return "unverified";
		}

	}

	@GetMapping("/findAll")
	public String findAll(Model model, HttpSession session) {
		// Page<Books>pages=service.findPage(0);
		List<Books> allBooks = service.findAllBooks();
		// int totalPage=pages.getTotalPages();
		// long totalItems=pages.getTotalElements();
		// model.addAttribute("currentPage", 0);
		// model.addAttribute("totalPages", totalPage);
		// model.addAttribute("totalItems", totalItems);
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("all", allBooks);
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "allBooks";
	}

	@GetMapping("/issue_form")
	public String issue_form(HttpSession session, Model model) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));

		return "issue_form";
	}

//	@PostMapping("/issue")
//	public String issue(@RequestParam("bid") String bid, @RequestParam("bname") String bname,
//			@RequestParam("sid") int sid, @RequestParam("sname") String sname, @RequestParam("days") int days,
//			Model model, HttpSession session) {
//
//		List<Books> l = service.check_book(bid, "Available");
//		student s = service.check_student(sid, "Verified");
//		model.addAttribute("list", session.getAttribute("request"));
//		model.addAttribute("count", session.getAttribute("count"));
//		model.addAttribute("categories", session.getAttribute("categories"));
//		model.addAttribute("user", session.getAttribute("user"));
//		model.addAttribute("countBooking", service.countBooking());
//		if (l.isEmpty()) {
//			model.addAttribute("type", "Book");
//			model.addAttribute("id", bid);
//			model.addAttribute("pic", session.getAttribute("pic"));
//			return "not_available";
//		}
//
//		else if (s == null) {
//			model.addAttribute("type", "Student");
//			model.addAttribute("id", sid);
//			model.addAttribute("pic", session.getAttribute("pic"));
//			return "not_available";
//		} else {
//			service.issue(bid, sid, sname, bname, days);
//			service.setStatus(bid, "issued");
//			return "issue_success";
//
//		}
//
//	}
	
	@PostMapping("/issue")
	public String issue(@RequestParam("bid") String bid,@RequestParam("sid") int sid, @RequestParam("days")int days,Principal princial ,Model model,HttpSession session) {
		service.issue(bid, sid, days,princial.getName());
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("countBooking", service.countBooking());
		service.setStatus(bid, "issued");
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "issue_success";
	}
	

//	@GetMapping("/add_student_form")
//	public String add_form(Model model,HttpSession session) {
//		model.addAttribute("list", session.getAttribute("request"));
//		model.addAttribute("count", session.getAttribute("count"));
//		return "add_student_form";
//	}

//	@PostMapping("/add_student")
//	public String save_studnet(  @RequestParam("sid")int sid, @RequestParam("sname")String name, @RequestParam("semester") int semester,
//			 @RequestParam("faculty") String faculty) {
//		
//		service.save_student(sid,name, semester, faculty);
//		return "add_student_Success";
//	}

//	@GetMapping ("/csit")
//	public String csit() {
//		return "csit";
//	}
//	
//	@GetMapping("/6_sem")
//	public String six_sem() {
//		return "6_sem";
//	}
//	
//	@GetMapping("/dot_net")
//	public String dot_net(Model model,HttpSession session) {
//			session.setAttribute("bname",".net");
//		 long count_available=service.countBook(".net","Available");
//		 model.addAttribute("count", count_available);
//		 long count_issued=service.countBook(".net", "issued");
//		 model.addAttribute("count_issued", count_issued);
//		return "details";
//	}
//	@GetMapping("/java")
//	public String java(Model model,HttpSession session) {
//			session.setAttribute("bname","java");
//		 long count_available=service.countBook("java","Available");
//		 model.addAttribute("count", count_available);
//		 long count_issued=service.countBook("java", "issued");
//		 model.addAttribute("count_issued", count_issued);
//		return "details";
//	}
//	
	@GetMapping("/search")
	public String search(@RequestParam("search") String id, @RequestParam("search") String name, Model model,
			HttpSession session) {

		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));

		model.addAttribute("countBooking", service.countBooking());
		List<Books> blist = service.findByIdOrName(id, name);

		model.addAttribute("blist", blist);
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));

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
	public String update_form(@RequestParam("id") String id, Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));

		Books b = service.update_value(id);
		model.addAttribute("values", b);
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "updateForm";
	}

	@PostMapping("/update_save")
	public void update_save(@RequestParam("bid") String bid, @RequestParam("bname") String bname,
			@RequestParam("category") String category, @RequestParam("author") String author,
			@RequestParam("publication") String publication, HttpServletResponse response, Model model,
			HttpSession session) {

		service.update_save(bid, bname, category, author, publication);
		try {
			model.addAttribute("list", session.getAttribute("request"));
			model.addAttribute("count", session.getAttribute("count"));
			model.addAttribute("categories", session.getAttribute("categories"));
			model.addAttribute("user", session.getAttribute("user"));
			model.addAttribute("expired_count", session.getAttribute("expired_count"));
			model.addAttribute("issue_count", session.getAttribute("issue_count"));
			

			response.sendRedirect("/Admin/findAll");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@GetMapping("/delete")
	public void delete(@RequestParam("id") String id, HttpServletResponse response, Model model, HttpSession session)
			throws IOException {

		Books b= service.findBookById(id);
		String status = b.getStatus();
		issue i=service.findIssueByBid(id);
		String s = "issued";
		if (status.equals(s)) {
			service.setStatus(b.getBid(), "Available");
			service.idelete(i.getIssueID());
		} else if (status.equals("Booked")) {
			Booking bb = service.findByBid(b.getBid());
			service.cancel_booking(bb.getBooking_id());
			//service.bdelete(bid);
		} else {
			service.bdelete(id);
		}
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		response.sendRedirect("/Admin/findAll");
	}

	@GetMapping("/show_requests")
	public String requests(Model model, HttpSession session) {
		List<student> list = service.request("Unverified");
		int count = list.size();
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());

		return "request";
	}

	@GetMapping("/verify")
	public void verify(@RequestParam("id") int sid, HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		service.verify("Verified", sid);
		student s = service.findStudent(sid);
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
	
		service.mail(s.getEmail(),
				"Dear" + s.getUsername() + ", You are now registered user of Library Management System", "Verified");
		// Library","verficication");
		response.sendRedirect("/Admin/show_requests");

	}

	@GetMapping("/reject")
	public void reject(@RequestParam("id") int sid, HttpServletResponse response,Model model,HttpSession session) throws IOException {
		student s = service.findStudent(sid);
		service.mail(s.getEmail(), "Dear, " + s.getUsername() + "Registration for Library Management System failed",
				"Rejected");
		service.reject(sid);
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		response.sendRedirect("/Admin/show_requests");
	}

//	@GetMapping("/findLatest")
//	public String findLatest() {
//		Books b=service.findLatest();
//		System.out.println(b.getBid());
//		return "latest";
//	}

	@GetMapping("/students")
	public String view_student(Model model, HttpSession session) {
		List<student> slist = service.find_student("USER", "Verified");
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("user_role", "Users");
		model.addAttribute("slist", slist);
		return "student";
	}

	@GetMapping("/stuForm")
	public String addstu(HttpSession session, Model model) {
		session.setAttribute("role", "USER");
		model.addAttribute("student", new student());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());

		return "add_student_form";
	}

	@PostMapping("/stuSave")
	public String stuSave(@Valid @ModelAttribute(value = "student") student stu, BindingResult result,
			@RequestParam("image") MultipartFile file, HttpSession session, Model model)
			throws IllegalStateException, IOException {
		String role = (String) session.getAttribute("role");
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		if (result.hasErrors()) {
			return "add_student_form";
		} else {
			service.stuSave(stu, role, file);
			return "add_student_form";
		}

	}

	@GetMapping("/admin_form")
	public String addAdminForm(HttpSession session, Model model) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
	
		session.setAttribute("role", "ADMIN");
		return "add_admin_form";
	}

	@GetMapping("/findAdmins")
	public String findAdmins(Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("user_role", "Admins");
		List<student> slist = service.find_student("ADMIN", "Verified");
		model.addAttribute("slist", slist);
		model.addAttribute("countBooking", service.countBooking());
		return "student";
	}

	@GetMapping("/update_admin")
	public String update_admin(Model model, HttpSession session, @RequestParam("id") int id) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());

		student s = service.findStudent(id);
		model.addAttribute("admin", s);
		return "admin_update_form";
	}

	@PostMapping("/student_update_save")
	public void admin_update_save(@RequestParam("sid") int sid, @RequestParam("username") String username,
			@RequestParam("address") String address, @RequestParam("email") String email,
			@RequestParam("phone") String phone, HttpServletResponse response, HttpSession session, Model model)
			throws IOException {
		service.admin_update_save(sid, username, address, email, phone);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		String role=service.findStudent(sid).getRole();
		if(role.equals("ADMIN")) {
			response.sendRedirect("/Admin/findAdmins");
		}
		else {
			response.sendRedirect("/Admin/students");
		}

	}

	@GetMapping("/student_delete")
	public void delete_student(@RequestParam("id") int sid, HttpServletResponse response, Model model,
			HttpSession session) throws IOException {
		service.delete_student(sid);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		response.sendRedirect("/Admin/students");

	}

	@GetMapping("/details")
	public String book_details(@RequestParam("name") String bname, Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		List<Books> list = service.findByName(bname);
		// com.example.demo.entity.issue i=service.issue_details(bid);
		model.addAttribute("issue_details", session.getAttribute("mo"));
		model.addAttribute("h", "hello");
		model.addAttribute("pic", session.getAttribute("pic"));
		model.addAttribute("blist", list);
		return "book_details";

	}

	@GetMapping("/issue_search")
	public String issue_search(@RequestParam("id") String bid, Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
	
		Books b = service.issue_search(bid);
		model.addAttribute("issue", b);
		return "issue";
	}

	@GetMapping("/category/{category_id}")
	public String fiction(Model model, HttpSession session, @PathVariable("category_id") int id) {
		com.example.demo.entity.category categories = service.findCategoryById(id);
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("category_name", categories.getName());
		List<BookCountDTO> bcd = service.list(categories.getName());
		model.addAttribute("groupby", bcd);
		return "book_category";
	}

//	 @GetMapping("/horror")
//	 public String horror(Model model) {		 
//		List<BookCountDTO>bcd=service.list("horror");
//		model.addAttribute("groupby", bcd);
//		 return "book_category";
//	 }
//	 @GetMapping("/poetry")
//	 public String poetry(Model model) {		 
//		List<BookCountDTO>bcd=service.list("poetry");
//		model.addAttribute("groupby", bcd);
//		 return "book_category";
//	 }
//	 @GetMapping("/drama")
//	 public String drama(Model model) {		 
//		List<BookCountDTO>bcd=service.list("drama");
//		model.addAttribute("groupby", bcd);
//		 return "book_category";
//	 }
//	 @GetMapping("/history")
//	 public String history(Model model) {		 
//		List<BookCountDTO>bcd=service.list("history");
//		model.addAttribute("groupby", bcd);
//		 return "book_category";
//	 }
//	 
	@GetMapping("/issue_details")
	public String issue_details(@RequestParam("id") String bid, Model model, HttpSession session) {
		System.out.println("hello");
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		com.example.demo.entity.issue i = service.issue_details(bid);
		model.addAttribute("issue_details", i);
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "issue_details";
	}

	@GetMapping("/category")
	public String category(Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		List<com.example.demo.entity.category> list = service.categoryAll();
		model.addAttribute("clist", list);
		return "category";
	}

	@PostMapping("/add_category")
	public void add_book(@RequestParam("cname") String cname, HttpServletResponse response, HttpSession session,
			Model model) throws IOException {
		service.add_category(cname);
		List<com.example.demo.entity.category> category_list = service.get_categories();
		session.setAttribute("categories", category_list);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		response.sendRedirect("/Admin/category");
	}

	@GetMapping("/update_category")
	public String update_category_form(@RequestParam("id") int cid, Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
	
		com.example.demo.entity.category c = service.update_form(cid);
		model.addAttribute("update_category", c);

		return "update_category_form";
	}

	@PostMapping("/update_category_save")
	public void update_category_save(@RequestParam("cid") int cid, @RequestParam("name") String name,
			HttpServletResponse response, Model model, HttpSession session) throws IOException {
		service.category_update_save(cid, name);
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		List<com.example.demo.entity.category> category_list = service.get_categories();
	
		session.setAttribute("categories", category_list);
		response.sendRedirect("/Admin/category");
	}

	@GetMapping("/delete_category")
	public void category_delete(@RequestParam("id") int cid, HttpServletResponse response, Model model,
			HttpSession session) throws IOException {
		service.category_delete(cid);
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());

		List<com.example.demo.entity.category> category_list = service.get_categories();
		session.setAttribute("categories", category_list);
		response.sendRedirect("/Admin/category");

	}

	@GetMapping("/bookings")
	public String bookings(Model model, HttpSession session) {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
	
		model.addAttribute("countBooking", service.countBooking());
		List<Booking> blist = service.bookings();
		model.addAttribute("blist", blist);
		return "Bookings";
	}

	@GetMapping("/cancelBooking")
	public void cancel_Booking(@RequestParam("id") int booking_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		Booking b = service.findBooking(booking_id);
		service.cancel_booking(booking_id);
		service.setStatus(b.getBid(), "Available");
		System.out.println(b.getBid());
		response.sendRedirect("/Admin/bookings");
	}

	@GetMapping("/issueBooking")
	public void issue_Booking(@RequestParam("id") int Booking_id, Model model, HttpSession session,Principal principle,
			HttpServletResponse response) throws IOException {
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		Booking b = service.findBooking(Booking_id);

		service.setStatus(b.getBid(), "issued");
		service.cancel_booking(Booking_id);
		service.issue(b.getBid(), b.getUser_id(),7,principle.getName());

		response.sendRedirect("/Admin/bookings");
	}

	@GetMapping("/bookingById")
	public String findBookingById(@RequestParam("id") String bid, Model model, HttpSession session) {
		Booking b = service.findByBid(bid);
		model.addAttribute("blist", b);
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("countBooking", service.countBooking());
		return "Bookings";
	}

	@GetMapping("/book/{id}")
	public String showBookDetails(@PathVariable("id") String id, Model model, HttpSession session) {
		//com.example.demo.entity.issue book = service.getIssuedBookById(id); // Assuming you have a service to retrieve
																			// book details
		//model.addAttribute("book", book);
		model.addAttribute("h", "hello");
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "book_details"; // Thymeleaf template for book details
	}

	@GetMapping("/search_book")
	public String search_book(@RequestParam("search") String search, Model model, HttpSession session) {

		List<Books> ll = service.findByIdOrName(search, search);
		model.addAttribute("all", ll);
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "allBooks";
	}
	
	@GetMapping("/expired")
	public String expired(Model model,HttpSession session) {
		List<issue> list= service.expired();
		model.addAttribute("expired", list);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		return "expired";
	}
	
	@GetMapping("/expire_mail/{id}")
	public void expired_mail(Model model,HttpSession session, @PathVariable("id") long id,HttpServletResponse response ) throws IOException {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		service.mail(service.findIssueById(id).getStudent().getEmail(), "Dear, "+service.findIssueById(id).getStudent().getUsername()+"Expiry date of Rented Book has reached on"
				+ service.findIssueById(id).getExpiry_date(),"Expired!");
		response.sendRedirect("/Admin/expired");
	}
	
	@GetMapping("/issued_list")
	public String issued_list(Model model,HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("issued_list", service.issued_list());
		return "issued_list";
	}
	
	@GetMapping("/search_issuedList")
	public String search_issuedList(@RequestParam("search")String search ,Model model,HttpSession session) {
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("countBooking", service.countBooking());
		model.addAttribute("list", session.getAttribute("request"));
		model.addAttribute("count", session.getAttribute("count"));
		model.addAttribute("categories", session.getAttribute("categories"));
		model.addAttribute("expired_count", session.getAttribute("expired_count"));
		model.addAttribute("issue_count", session.getAttribute("issue_count"));
		model.addAttribute("issued_list", service.searchIssueList(search));
		return "issued_list";
	}
	
	@PostMapping("/csv_saveBooks")
	public void csvSave(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException, CsvException {
		service.save_csv(file);
		response.sendRedirect("/Admin/findAll");
	}

}
