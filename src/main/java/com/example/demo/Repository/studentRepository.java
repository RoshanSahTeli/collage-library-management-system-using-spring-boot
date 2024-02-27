package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.student;

public interface studentRepository extends JpaRepository<student, Integer> {
	
//	@Query("select i from issue i where i.")
//	public List<issue> l=
	public student findByEmail(String email);
	
	public List<student> findByStatus(String status);
	
	@Modifying
	@Query("update student s set s.status= :sta where s.sid= :sid")
	public void updateVerification(@Param("sta")String status,@Param("sid")int id);

}
