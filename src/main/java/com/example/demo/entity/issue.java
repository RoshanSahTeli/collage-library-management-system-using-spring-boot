package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	
	
	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	private int sid;
	
	private String sname;
	
	
	
	private LocalDateTime issue_date;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
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

	

	

	public LocalDateTime getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(LocalDateTime issue_date) {
		this.issue_date = issue_date;
	}

	@Override
	public String toString() {
		return "issue [bid=" + bid + ", sid=" + sid + ", sname=" + sname 
				+ ", issue_date=" + issue_date + "]";
	}

	public issue(String bid, int sid, String sname, LocalDateTime issue_date,String bname) {
		super();
		this.bid = bid;
		this.bname=bname;
		this.sid = sid;
		this.sname = sname;
		
		this.issue_date = issue_date;
	}

	public issue() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
