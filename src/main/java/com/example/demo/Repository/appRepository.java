package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Admins;

public interface appRepository extends JpaRepository<Admins, Integer>{
	
	

}
