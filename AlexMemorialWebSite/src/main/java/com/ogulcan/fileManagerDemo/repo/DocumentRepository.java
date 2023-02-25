package com.ogulcan.fileManagerDemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ogulcan.fileManagerDemo.domain.Document;


public interface DocumentRepository extends JpaRepository<Document, String> {

}
