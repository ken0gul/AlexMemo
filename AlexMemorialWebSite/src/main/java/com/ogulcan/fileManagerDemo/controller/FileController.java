package com.ogulcan.fileManagerDemo.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ogulcan.fileManagerDemo.domain.Document;
import com.ogulcan.fileManagerDemo.domain.User;
import com.ogulcan.fileManagerDemo.model.ResponseModel;
import com.ogulcan.fileManagerDemo.service.DocumentService;
import com.ogulcan.fileManagerDemo.service.UserService;

@Controller
public class FileController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String getHomePage(Model model) {
		List<Document> list = documentService.getAllFiles();
		List<String> imgList = new ArrayList<>();
		for(Document d : list) {
			System.out.println(d);
		}
		model.addAttribute("user", new User());
		model.addAttribute("img", imgList);
		model.addAttribute("list",list);
		System.out.println(list);
		return "index";
	}
	
	
	@PostMapping("/upload")
	
	public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes attr, User user) throws Exception {
	
		userService.saveUser(user);
	
		
		Document document = documentService.saveDocument(file, user);
		String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path(String.valueOf(document.getId()))
					.toUriString();
		ResponseModel response = new ResponseModel(document.getFileName(), downloadURL, file.getContentType(), file.getSize());
		
		// Display a success message on UI
		attr.addFlashAttribute("message", "Success!");
		
		
		return "redirect:/home";
	}

	
	@GetMapping("/download/{fileId}")
	@ResponseBody
	public ResponseEntity<Resource> download(@PathVariable String fileId) throws Exception {
		Document document = null;
		
		document = documentService.getFile(fileId);
		byte[] myData = document.getData();
		
		// Convert it into base64image
		
		String img = Base64.getEncoder().encodeToString(myData);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(document.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() +
				 "\"").body(new ByteArrayResource(document.getData()));
	}
	
}
