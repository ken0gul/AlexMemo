package com.ogulcan.fileManagerDemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogulcan.fileManagerDemo.domain.Document;
import com.ogulcan.fileManagerDemo.domain.User;
import com.ogulcan.fileManagerDemo.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}


	public List<User> getAllUsers() {
		
		
//		list.stream().map(i -> {
//			String img = Base64.getEncoder().encodeToString(i.getData());
//			i.setImg(img);
//			return i;
//		}).collect(Collectors.toList());
		return userRepository.findAll();
	}
}
