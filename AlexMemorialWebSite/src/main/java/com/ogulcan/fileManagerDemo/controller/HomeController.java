package com.ogulcan.fileManagerDemo.controller;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.ogulcan.fileManagerDemo.domain.User;
import com.ogulcan.fileManagerDemo.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/home")
	public String getHomePage(ModelMap model) {
		List<User> users = userService.getAllUsers();
		users.forEach(doc -> System.out.println(doc.getDocument().getFileType()));
		model.put("users", users);
		return "home";
	}
	
	
}
