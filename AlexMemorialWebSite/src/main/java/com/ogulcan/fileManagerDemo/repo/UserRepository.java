package com.ogulcan.fileManagerDemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ogulcan.fileManagerDemo.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
