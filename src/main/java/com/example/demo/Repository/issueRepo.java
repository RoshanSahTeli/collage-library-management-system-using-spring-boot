package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.issue;

public interface issueRepo extends JpaRepository<issue, Long> {
	
	@Query("select i from issue i where i.books.bid= :bid")
	public issue findIssueByBid(@Param("bid")String bid);
//	
	@Query("select i from issue i where i.student.sid= :sid")
	public List<issue> findIssued(@Param("sid")int sid);
//	
	@Query("select i from issue i where DATE(expiry_date)<=CURRENT_DATE")
	public List<issue> findExpired();
	
	
	



}
