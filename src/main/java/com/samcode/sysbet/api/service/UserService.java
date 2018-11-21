package com.samcode.sysbet.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.samcode.sysbet.api.entity.User;

@Component
public interface UserService {
	
	User findByUsername(String username);
	
	User createOrUpdate(User user);
	
	Optional<User> findById(Integer id);
	
	void delete(Integer id);
	
	Page<User> findAll(int page, int count);

}
