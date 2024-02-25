package com.example.demo.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class Books {
	
	@Id
	private String id;
	
	private String name;
	
	private String faculty;
	
	public Books(String id, String name, String faculty, LocalDate date, String status,
			com.example.demo.entity.student student) {
		super();
		this.id = id;
		this.name = name;
		this.faculty = faculty;
		this.date = date;
		this.status = status;
		this.student = student;
	}



	@Override
	public String toString() {
		return "Books [id=" + id + ", name=" + name + ", faculty=" + faculty + ", date=" + date + ", status=" + status
				+ ", student=" + student + "]";
	}



	private LocalDate date;
	 
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "sid")
	private student student;

	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getFaculty() {
		return faculty;
	}



	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}



	public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public student getStudent() {
		return student;
	}



	public void setStudent(student student) {
		this.student = student;
	}



	public Books() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
