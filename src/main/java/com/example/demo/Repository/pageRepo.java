package com.example.demo.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.entity.Books;

public interface pageRepo extends PagingAndSortingRepository<Books, String> {

}
