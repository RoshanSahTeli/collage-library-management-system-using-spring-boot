package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.appRepository;
import com.example.demo.entity.Admins;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private appRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Admins a=repo.findByEmail(username);
		if(a==null) {
			throw new UsernameNotFoundException("Username Not Found!!");
		}
		else {
			return new CustomerUser(a);
		}
	}

}
