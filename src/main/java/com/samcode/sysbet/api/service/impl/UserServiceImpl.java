package com.samcode.sysbet.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samcode.sysbet.api.entity.User;
import com.samcode.sysbet.api.repository.UserRepository;
import com.samcode.sysbet.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public User createOrUpdate(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return this.userRepository.findById(id);
	}

	@Override
	public void delete(Integer id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public Page<User> findAll(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.userRepository.findAll(pages);
	}

}
