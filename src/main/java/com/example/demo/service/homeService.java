package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.studentRepository;
import com.example.demo.entity.student;

@Component
public class homeService {
	
	@Autowired
	private studentRepository repo;
	
	public void save_signup(student stu,MultipartFile file) throws IllegalStateException, IOException {
		
		
		String folderPath="C:\\Users\\rajes\\OneDrive\\Desktop\\New folder\\Library\\src\\main\\resources\\static\\images\\";
		String path=folderPath+file.getOriginalFilename();
		String sc="images\\";
		int i=path.indexOf(sc);
		String imgPath=path.substring(i+sc.length());
		student s=new student();
		file.transferTo(new File(path));
		s.setEmail(stu.getEmail());
		s.setSid(stu.getSid());
		s.setPassword(new BCryptPasswordEncoder().encode(stu.getPassword()));
		s.setPhone(stu.getPhone());
		s.setUsername(stu.getUsername());
		s.setAddress(stu.getAddress());
		s.setStatus("Unverified");
		s.setRole("USER");
		s.setImg(imgPath);
		
		repo.save(s);
		
	}
	
	public student userStatus(String email) {
		 student s= repo.findByEmail(email);
		 return s;
	}
//	public List<student> request(String status){
//		List<student>list=repo.findByStatus(status);
//		return list;
//	}

}
