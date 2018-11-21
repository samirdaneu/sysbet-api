package com.samcode.sysbet.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.samcode.sysbet.api.entity.Competition;

@Component
public interface CompetitionService {
	
	Competition createOrUpdate(Competition competition);
	
	Optional<Competition> findById(Integer id);
	
	void delete(Integer id);
	
	Page<Competition> findAll(int page, int count);
	
	Page<Competition> findByParameters(int page, int count, String name);

}
