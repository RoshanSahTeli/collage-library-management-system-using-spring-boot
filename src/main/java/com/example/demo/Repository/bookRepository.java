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
    @Query("update Books b set b.status = :newStatus where b.bid = :bookId")
    void setStatus(@Param("bookId") String bookId, @Param("newStatus") String newStatus);
	
	@Modifying
	@Query("select  b from Books b where b.bid= :bid")
	public List<Books>bList(@Param("bid")String bid);
	
	@Query("select count(c) from Books c where c.bname= :name and c.status= :status")
	public long countBook(@Param("name")String name,@Param("status")String status);
	
	public List<Books> findByBnameAndStatus(String bname,String status);
	
//	@Modifying
//	@Query("update Books b set b.bname= :bname , b.faculty= :faculty where b.bid= :bid")
//	 void update_save(@Param("bid")String id,@Param("bname")String bname,@Param("faculty")String faculty);
	
	@Query("select i from Books i where i.bid= :bid or i.bname= :bname and i.status= :status")
	public List<Books>findByIdOrName(@Param("bid")String bid,@Param("bname")String bname,
			@Param("status")String status);
	
	public Books findFirstByBnameOrderByDateDesc(String name);
}
