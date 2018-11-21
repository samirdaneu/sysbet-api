package com.samcode.sysbet.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.samcode.sysbet.api.entity.Team;

@Component
public interface TeamService {
	
	Team createOrUpdate(Team team);
	
	Optional<Team> findById(Integer id);
	
	void delete(Integer id);
	
	Iterable<Team> findAll();
	
	Page<Team> listTeams(int page, int count);
	
	Page<Team> findByParameters(int page, int count, String name);

}
