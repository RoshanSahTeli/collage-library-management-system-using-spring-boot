package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.category;

public interface CategoryRepo extends JpaRepository<category, Integer>{

}
