package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.issue;

public interface issueRepo extends JpaRepository<issue, String> {
	
	@Query("select i from issue i where i.bid= :bid")
	public List<issue> findByIdOrName(@Param("bid")String bid);

}
