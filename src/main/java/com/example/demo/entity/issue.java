package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "issue")
public class issue {
	
	
	@Id
	private String bid;
	
	private String bname;
	
	private int sid;
	
	private String sname;
	
	private String faculty;
	
	private int semester;
	private LocalDate issue_date;
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public LocalDate getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(LocalDate issue_date) {
		this.issue_date = issue_date;
	}
	@Override
	public String toString() {
		return "issue [bid=" + bid + ", bname=" + bname + ", sid=" + sid + ", sname=" + sname + ", faculty=" + faculty
				+ ", semester=" + semester + ", issue_date=" + issue_date + "]";
	}
	public issue(String bid, String bname, int sid, String sname, String faculty, int semester,
			LocalDate issue_date) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.sid = sid;
		this.sname = sname;
		this.faculty = faculty;
		this.semester = semester;
		this.issue_date = issue_date;
	}
	public issue() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
