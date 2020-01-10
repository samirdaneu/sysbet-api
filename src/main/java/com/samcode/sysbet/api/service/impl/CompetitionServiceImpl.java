package com.samcode.sysbet.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samcode.sysbet.api.entity.Competition;
import com.samcode.sysbet.api.repository.CompetitionRepository;
import com.samcode.sysbet.api.service.CompetitionService;

@Service
public class CompetitionServiceImpl implements CompetitionService {
	
	@Autowired
	private CompetitionRepository competitionRepository;

	@Override
	public Competition createOrUpdate(Competition competition) {
		return this.competitionRepository.save(competition);
	}

	@Override
	public Optional<Competition> findById(Integer id) {
		return this.competitionRepository.findById(id);
	}

	@Override
	public void delete(Integer id) {
		this.competitionRepository.deleteById(id);
	}

	@Override
	public Page<Competition> findAll(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.competitionRepository.findAll(pages);
	}

	@Override
	public Page<Competition> findByParameters(int page, int count, String name) {
		Pageable pages = PageRequest.of(page, count);
		return this.competitionRepository.findByNameIgnoreCaseOrderByNameDesc(pages, name);
	}

	@Override
	public Page<Competition> listCompetitions(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.competitionRepository.findAll(pages);
	}

}
