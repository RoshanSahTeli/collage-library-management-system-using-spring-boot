package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Rating;

public interface ratingRepo extends JpaRepository<Rating,Integer> {
	@Query("select AVG(r.ratingValue) from Rating r where r.book.bname= :bname ")
	public int findRatingByBookName(@Param("bname")String bname);
}
