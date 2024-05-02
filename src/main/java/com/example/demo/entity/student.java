package com.example.demo.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name ="student" )
public class student {
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@NotBlank(message = "ID should not be blank!")
	private int sid;
	
	@NotBlank(message = "Username should not be blank!")
	
	private String username;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", message = "Invalid email format")
	private String email;
	@NotBlank
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace")
	private String password;
	
	@NotBlank
	@Size(max = 10,min = 10,message = "Length should be of exactly 10 digits")
	private String phone;
	@NotBlank
	private String address;
	private String role;
	private String status;
	
	private String img;
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Books> getBookList() {
		return bookList;
	}
	public void setBookList(List<Books> bookList) {
		this.bookList = bookList;
	}
	public student(int sid, String username, String email, String password, String phone, String address, String role,
			String status,String img, List<Books> bookList) {
		super();
		this.sid = sid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.status = status;
		this.bookList = bookList;
		this.img=img;
	}
	public student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
}

	