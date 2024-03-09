package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cid;
	
	private String name;

	public int getId() {
		return cid;
	}

	public void setId(int id) {
		this.cid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "category [id=" + cid + ", name=" + name + "]";
	}

	public category(int id, String name) {
		super();
		this.cid = id;
		this.name = name;
	}

	public category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
