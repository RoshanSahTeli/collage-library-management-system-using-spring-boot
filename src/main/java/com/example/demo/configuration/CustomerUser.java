package com.example.demo.configuration;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.student;

public class CustomerUser implements UserDetails {

		student s;
		
	public CustomerUser(student s) {
			super();
			this.s = s;
		}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(s.getRole());
		// TODO Auto-generated method stub
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return s.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return s.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
