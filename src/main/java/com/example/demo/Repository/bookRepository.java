package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.BookCountDTO;
import com.example.demo.entity.Books;
import com.example.demo.entity.issue;

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
	@Query("select i from Books i where i.bid= :bid or i.bname= :bname and i.status= :status")
	public List<Books>findByIdOrName(@Param("bid")String bid,@Param("bname")String bname,
			@Param("status")String status);
	
	public List<Books> findByBidOrBname(String bid,String bname);
	
	public Books findFirstByBnameOrderByDateDesc(String name);


	
	//@Query("select s from Books s where s.category= :cat")
	List<Books> findByCategory(String category);
	
	@Query("SELECT new com.example.demo.entity.BookCountDTO(b.bname, " +
		       "COUNT(b) FILTER (WHERE b.status = 'Available'), " +
		       "COUNT(b) FILTER (WHERE b.status = 'issued'), " +
		       "COUNT(b) FILTER (WHERE b.status = 'booked'), " +
		       "COALESCE(AVG(r.ratingValue), 0)) " + 
		       "FROM Books b " +
		       "LEFT JOIN Rating r ON b.bid = r.book.bid " + 
		       "WHERE b.category = :category " +
		       "GROUP BY b.bname")
	List<BookCountDTO> countBooksByName(@Param("category") String category);
	
	public List<Books> findByBname(String name);
	
	@Query("SELECT new com.example.demo.entity.BookCountDTO(" +
		       "b.bname, " + 
		       "COUNT(b), " + 
		       "COALESCE(AVG(r.ratingValue), 0)) " + 
		       "FROM Books b " +
		       "LEFT JOIN Rating r ON b.bid = r.book.bid " + 
		       "WHERE b.category = :category " + 
		       "GROUP BY b.bname")
	List<BookCountDTO> findAvailableBooksByNameAndCategory(@Param("category") String category);
	
	
	List<Books> findByStatus(String status);
	
	@Query("select b from Books b where (b.bid = :input or str(b.issue.issueID) = :input) and b.status = 'issued'")
	public List<Books> searchByIssueIdOrBookId(@Param("input") String input);


	
}
