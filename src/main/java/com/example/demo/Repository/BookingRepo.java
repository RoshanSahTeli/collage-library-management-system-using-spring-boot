package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer> {
//	
//	@query("select b from booking b where b.username= :username")
	public List<Booking> findByUsername(String username);

}
