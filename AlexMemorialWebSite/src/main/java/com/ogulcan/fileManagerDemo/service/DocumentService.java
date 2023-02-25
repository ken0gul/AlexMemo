package com.ogulcan.fileManagerDemo.service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ogulcan.fileManagerDemo.domain.Document;
import com.ogulcan.fileManagerDemo.domain.User;
import com.ogulcan.fileManagerDemo.repo.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository repository;
	
	
	public Document saveDocument(MultipartFile file, User user) throws Exception {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String img = Base64.getEncoder().encodeToString(file.getBytes());
		
		
		Document document = new Document(fileName, file.getContentType(), file.getBytes(), new Date(), file.getSize(), user,img);
		
		
		return repository.save(document);
	}
	
	
	public Document getFile(String fileId) throws Exception {
		
		return repository.findById(fileId).orElseThrow(() -> new Exception("File not found"));
	}
	
	
	public List<Document> getAllFiles() {
//		String img = Base64.getEncoder().encodeToString(file.getBytes());
		 List<Document> list =repository.findAll();
		return list.stream().map(i -> {
			String img = Base64.getEncoder().encodeToString(i.getData());
			i.setImg(img);
			return i;
		}).collect(Collectors.toList());
		 
	}
}
