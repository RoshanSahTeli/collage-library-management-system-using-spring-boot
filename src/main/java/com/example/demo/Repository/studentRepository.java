package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.student;

public interface studentRepository extends JpaRepository<student, Integer> {
	
//	@Query("select i from issue i where i.")
//	public List<issue> l=

}
