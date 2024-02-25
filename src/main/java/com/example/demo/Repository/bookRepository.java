package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Books;

public interface bookRepository extends JpaRepository<Books, String> {
	
	@Query("select b from Books b where b.id= :i and b.status= :s")
	public List<Books>forIssue( @Param("i") String bid, @Param("s")String status);
	
	
	@Modifying
    @Query("update Books b set b.status = :newStatus where b.id = :bookId")
    void setStatus(@Param("bookId") String bookId, @Param("newStatus") String newStatus);
	
	@Modifying
	@Query("select  b from Books b where b.id= :bid")
	public List<Books>bList(@Param("bid")String bid);
	
	@Query("select count(c) from Books c where c.name= :name and c.status= :status")
	public long countBook(@Param("name")String name,@Param("status")String status);
	
	public List<Books> findByNameAndStatus(String bname,String status);
	
	@Modifying
	@Query("update Books b set b.name= :bname , b.faculty= :faculty where b.id= :bid")
	 void update_save(@Param("bid")String id,@Param("bname")String bname,@Param("faculty")String faculty);
}
