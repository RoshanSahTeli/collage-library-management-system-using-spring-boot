package com.example.demo.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="student" )
public class student {
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;
	
//	@NotBlank
//	@Size(max = 10,min = 3)
	private String username;
	
//	@NotBlank
	//@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "invalid email")
	private String email;
	
//	@NotBlank
//	@Size(max = 10,min = 4)
	private String password;
	
	
	private String phone;
	
	private String role;
	
	
	public student( int sid,  String username,
			 String email,
			 String password, String phone, String role, String status,
			List<Books> bookList) {
		super();
		this.sid = sid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.status = status;
		this.bookList = bookList;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
	public List<Books>bookList=new ArrayList<>();

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Books> getBookList() {
		return bookList;
	}

	public void setBookList(List<Books> bookList) {
		this.bookList = bookList;
	}

	@Override
	public String toString() {
		return "student [sid=" + sid + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", bookList=" + bookList + "]";
	}

	

	public student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

	