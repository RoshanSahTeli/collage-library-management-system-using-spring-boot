package com.example.demo.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="student" )
public class student {
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int sid;
	
	private String name;
	
	private int semester;
	
	private String faculty;
	
	@OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
	public List<Books>bookList=new ArrayList<>();
	

	public student(int sid, String name, int semester, String faculty, List<Books> bookList) {
		super();
		this.sid = sid;
		this.name = name;
		this.semester = semester;
		this.faculty = faculty;
		this.bookList = bookList;
	}


	@Override
	public String toString() {
		return "student [sid=" + sid + ", name=" + name + ", semester=" + semester + ", faculty=" + faculty
				+ ", bookList=" + bookList + "]";
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getSemester() {
		return semester;
	}


	public void setSemester(int semester) {
		this.semester = semester;
	}


	public String getFaculty() {
		return faculty;
	}


	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}


	public List<Books> getBookList() {
		return bookList;
	}


	public void setBookList(List<Books> bookList) {
		this.bookList = bookList;
	}


	public student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
