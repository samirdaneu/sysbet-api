package com.samcode.sysbet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samcode.sysbet.api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> { 
	
	User findByUsername(String username);
		
}
