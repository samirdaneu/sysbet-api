package com.samcode.sysbet.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.samcode.sysbet.api.entity.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
	
	Page<Competition> findByNameIgnoreCaseOrderByNameDesc(Pageable pages,String name);

}
