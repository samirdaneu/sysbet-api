package com.samcode.sysbet.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samcode.sysbet.api.entity.Team;
import com.samcode.sysbet.api.repository.TeamRepository;
import com.samcode.sysbet.api.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {
	
	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Team createOrUpdate(Team team) {
		return this.teamRepository.save(team);
	}

	@Override
	public Optional<Team> findById(Integer id) {
		return this.teamRepository.findById(id);
	}

	@Override
	public void delete(Integer id) {
		this.teamRepository.deleteById(id);
	}

	@Override
	public Iterable<Team> findAll() {
		return this.teamRepository.findAll();
	}

	@Override
	public Page<Team> listTeams(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.teamRepository.findAll(pages);
	}

	@Override
	public Page<Team> findByParameters(int page, int count, String name) {
		Pageable pages = PageRequest.of(page, count);
		return this.teamRepository.findByNameIgnoreCaseOrderByNameDesc(pages, name);
	}

}
