package com.example.demo.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.BookingRepo;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.bookRepository;
import com.example.demo.Repository.issueRepo;
import com.example.demo.Repository.pageRepo;
import com.example.demo.Repository.ratingRepo;
import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Books;
import com.example.demo.entity.category;
import com.example.demo.entity.issue;
import com.example.demo.entity.student;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import jakarta.transaction.Transactional;

@Component
public class appService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private bookRepository brepo;

	@Autowired
	private studentRepository srepo;

	@Autowired
	private issueRepo irepo;
	@Autowired
	private CategoryRepo crepo;

	@Autowired
	private BookingRepo borepo;
	@Autowired
	private pageRepo prepo;
	
	@Autowired
	private ratingRepo rrepo;

	public void add_book(String name, String author, String publication, String Category) {
		Books b = new Books();

		Books boo = brepo.findFirstByBnameOrderByDateDesc(name);
		if (boo == null) {
			String id = name.replaceAll("\\s", "") + 1;
			b.setBid(id);
		} else {
			String bname = boo.getBid();
			String new_name = name.replace(" ", "");
			int name_length = new_name.length();
			int num = Integer.parseInt(bname.substring(name_length)) + 1;
			System.out.println(num);
			String id = new_name + num;
			b.setBid(id);
		}

		b.setAdd_date(LocalDateTime.now());
		b.setAuthor(author);
		b.setPublication(publication);
		b.setBname(name);
		b.setStatus("Available");
		b.setCategory(Category);
		brepo.save(b);
	}

//	public void issue(String bid,int sid,String sname,String bname,int days) {
//		issue i=new issue();
//		
//		student ss=srepo.findById(sid).get();
//		List<Books>bList=brepo.bList(bid);
//		student s=new student();
//		for(Books book:bList) {
//			book.setAdd_date(book.getAdd_date());
//			book.setCategory(book.getCategory());
//			book.setBid(bid);
//			book.setBname(bname);
//			book.setAuthor(book.getAuthor());
//			book.setPublication(book.getPublication());
//			book.setStudent(s);
//			s.getBookList().add(book);
//		}
//		s.setSid(sid);
//		s.setEmail(ss.getEmail());
//		s.setPassword(ss.getPassword());
//		s.setPhone(ss.getPhone());
//		s.setRole(ss.getRole());
//		s.setSid(ss.getSid());
//		s.setStatus(ss.getStatus());
//		s.setUsername(ss.getUsername());
//		s.setAddress(ss.getAddress());
//		s.setBookList(bList);
//		srepo.save(s);
//
//		
//		i.setBid(bid);
//		i.setBname(bname);
//		i.setSid(sid);
//		i.setSname(sname);
//		i.setIssue_date(LocalDateTime.now());
//		i.setExpiry_date(LocalDateTime.now().plusDays(days));
//		irepo.save(i);
//		
//		
//	}

	public void issue(String bid, int sid, int days, String email) {
		issue i = new issue();
		i.setBooks(brepo.findById(bid).get());
		i.setStudent(srepo.findById(sid).get());
		i.setIssue_date(LocalDate.now());
		i.setExpiry_date(LocalDate.now().plusDays(days));
		i.setIssuedBy(srepo.findByEmail(email));
		irepo.save(i);
	}

	public List<Books> check_book(String bid, String status) {
		List<Books> l = brepo.forIssue(bid, "Available");
		return l;
	}

	public student check_student(int sid, String status) {
		student s = srepo.findBySidAndStatus(sid, status);
		return s;
	}

	@Transactional
	public void setStatus(String id, String status) {
		brepo.setStatus(id, status);
	}

	public long countBook(String name, String status) {

		long count = brepo.countBook(name, status);
		return count;
	}

//	public List<com.example.demo.entity.issue> searchIssuedBook(String bid){
//		List<com.example.demo.entity.issue>list=irepo.findByIdOrName(bid);
//		return list;
//	}

	public List<Books> searchAvailableBook(String bid, String name, String status) {
		List<Books> list = brepo.findByIdOrName(bid, name, status);
		return list;
	}

	public List<Books> findByIdOrName(String bid, String bname) {
		return brepo.findByBidOrBname(bid, bname);
	}

	public List<Books> findByNameAndStatus(String bname, String status) {
		List<Books> list = brepo.findByBnameAndStatus(bname, status);
		return list;
	}

	public Books update_value(String id) {
		Books b = brepo.findById(id).get();
		return b;
	}

	public void update_save(String bid, String bname, String Category, String author, String publication) {
		Books bo = brepo.findById(bid).get();

		Books b = new Books();
		b.setCategory(Category);
		b.setBid(bid);
		b.setBname(bname);
		b.setAuthor(author);
		b.setPublication(publication);
		b.setAdd_date(bo.getAdd_date());
		b.setStatus(bo.getStatus());
		brepo.save(b);
	}

	@Transactional
	public void bdelete(String bid) {
		Books b=brepo.findById(bid).orElseThrow(() -> new RuntimeException("Book Not Found"));
		if(b.getIssue()!=null) {
			b.getIssue().setBooks(null);
		}
		if(b.getStudent()!=null) {
			b.getStudent().getBookList().remove(b);
		}
		brepo.delete(b);
	}

	@Transactional
	public void idelete(long id) {
		// Retrieve the issue from the database
		issue issue = irepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));

		// Break relationships with associated entities
		if (issue.getBooks() != null) {
			issue.getBooks().setIssue(null); // Remove reference from Books
		}

		if (issue.getStudent() != null) {
			issue.getStudent().getIssues().remove(issue); // Remove from Student's issue list
		}

		// Delete the issue
		irepo.delete(issue);
	}

	public Books find(String bid) {
		Books b = brepo.findById(bid).get();
		return b;
	}

	public List<student> request(String status) {
		List<student> list = srepo.findByStatus(status);
		return list;
	}

	@Transactional
	public void verify(String status, int id) {
		srepo.updateVerification(status, id);
	}

	public student findStudent(int id) {
		student s = srepo.findById(id).get();
		return s;
	}

	public void mail(String to, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("Roshanshah920@gmail.com");
		message.setTo(to);
		message.setText(body);
		message.setSubject(subject);
		javaMailSender.send(message);

	}

	public void reject(int id) {
		srepo.deleteById(id);
	}

	public Books issue_search(String bid) {
		Books b = brepo.findById(bid).get();
		return b;
	}

	public student findAdmin(String username) {
		return srepo.findByEmail(username);
	}

	public List<Books> findAllBooks() {
		return brepo.findAll();
	}

	public Page<Books> findPage(int page) {
		Pageable pageable = PageRequest.of(page, 7);
		return brepo.findAll(pageable);
	}

	public List<student> find_student(String role, String status) {
		return srepo.findByRoleAndStatus(role, status);
	}

	public void stuSave(student stu, String role, MultipartFile file) throws IllegalStateException, IOException {
		String FolderPath = "/home/roshan-sah-teli/spring/Library/src/main/resources/static/images/";
		String path = FolderPath + file.getOriginalFilename();
		String sc = "images/";
		int i = path.indexOf(sc);
		String name = path.substring(i + sc.length());
		student s = new student();
		file.transferTo(new File(path));
		s.setEmail(stu.getEmail());
		s.setSid(stu.getSid());
		s.setPassword(new BCryptPasswordEncoder().encode(stu.getPassword()));
		s.setPhone(stu.getPhone());
		s.setUsername(stu.getUsername());
		s.setAddress(stu.getAddress());
		s.setStatus("Verified");
		s.setRole(role);
		s.setImg(name);
		srepo.save(s);

	}

	public List<Books> findCategory(String category) {
		return brepo.findByCategory(category);
	}

	public category findCategoryById(int id) {
		return crepo.findById(id).get();
	}

	public List<BookCountDTO> list(String category) {

		List<BookCountDTO> ll = brepo.countBooksByName(category);

		return ll;
	}

	public List<Books> findByName(String name) {
		return brepo.findByBname(name);
	}

	public com.example.demo.entity.issue issue_details(String bid) {
		issue i = (com.example.demo.entity.issue) irepo.findIssueByBid(bid);
		return i;
	}

	public List<category> categoryAll() {
		return crepo.findAll();

	}

	public void add_category(String cname) {
		category c = new category();
		c.setName(cname);
		crepo.save(c);
	}

	public category update_form(int cid) {
		return crepo.findById(cid).get();
	}

	public void category_update_save(int cid, String name) {
		category c = new category();
		c.setId(cid);
		c.setName(name);
		crepo.save(c);
	}

	public void category_delete(int cid) {
		crepo.deleteById(cid);
	}

	public List<category> category_dropdown() {
		return crepo.findAll();
	}

	public List<category> get_categories() {
		return crepo.findAll();
	}

	@Transactional
	public void admin_update_save(int sid, String username, String address, String email, String phone) {
		srepo.updateStu(username, address, email, phone, sid);

	}

	public void delete_student(int sid) {
		srepo.deleteById(sid);
	}

	public List<Booking> bookings() {

		return borepo.findAll();
	}

	public Booking findBooking(int id) {
		return borepo.findById(id).get();
	}

	public long countBooking() {
		return borepo.count();
	}

	public void cancel_booking(int id) {
		borepo.deleteById(id);
	}

	public Booking findByBid(String bid) {
		return borepo.findByBid(bid);
	}

	public List<Books> findBooksByStatus(String status) {
		return brepo.findByStatus(status);
	}

	public List<student> findbyRole(String role, String status) {
		return srepo.findByRoleAndStatus(role, status);
	}

	public issue findIssueById(long id) {
		return irepo.findById(id).get();
	}

	public List<issue> expired() {
		return irepo.findExpired();
	}

	public List<issue> issued_list() {
		return irepo.findAll();
	}

	public List<Books> searchIssueList(String input) {
		return brepo.searchByIssueIdOrBookId(input);
	}
	
	public Books findBookById(String id) {
		return brepo.findById(id).get();
	}
	
	public issue findIssueByBid(String bid) {
		return irepo.findIssueByBid(bid);
	}
	public void save_csv(MultipartFile file) throws IOException,CsvException{
		try(Reader reader=	new InputStreamReader(file.getInputStream());
			CSVReader csvreader= new CSVReader(reader)){
				List<String[]> rows = csvreader.readAll();
				List<Books> blist=new ArrayList<>();
				
				for(String[] row:rows) {
					Books b= new Books();
					b.setBid(row[0]);
					b.setAuthor(row[1]);
					b.setBname(row[2]);
					b.setCategory(row[3]);
					b.setPublication(row[4]);
					b.setAdd_date(LocalDateTime.now());
					b.setStatus("Available");
					blist.add(b);
				}
				brepo.saveAll(blist);
			}
	}
	
	public int findRatingByBookName(String name) {
		return rrepo.findRatingByBookName(name);
	}

}
