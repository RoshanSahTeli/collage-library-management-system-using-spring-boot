package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class demo {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("java"));
		
		String name="java";
		
		String input = "java001";
		int l=name.length();
		String s=input.substring(l);
		System.out.println(s);
		
     //xample index
        
        // Get the substring after the specified index
//        String substring = input.substring(index).trim();
//        System.out.println(substring);// Trimming to remove leading spaces
        
        // Split the substring into words
        
        // Print the words
        
	}

}
