package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.category;

public interface CategoryRepo extends JpaRepository<category, Integer>{
	List<category>  findFirst3ByOrderByNameDesc();

}
