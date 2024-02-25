package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Admins;

public interface appRepository extends JpaRepository<Admins, Integer>{
	
	public Admins findByEmail(String email);

}
