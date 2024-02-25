package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.issue;

public interface issueRepo extends JpaRepository<issue, String> {
	
	

}
