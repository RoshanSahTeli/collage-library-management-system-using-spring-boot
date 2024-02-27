package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.appRepository;
import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.Admins;
import com.example.demo.entity.student;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private studentRepository srepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 student s=srepo.findByEmail(username);
		if(s==null) {
			throw new UsernameNotFoundException("Username Not Found!!");
		}
		else {
			return new CustomerUser(s);
		}
	}

}
