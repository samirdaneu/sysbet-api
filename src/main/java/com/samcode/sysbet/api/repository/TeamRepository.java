package com.samcode.sysbet.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.samcode.sysbet.api.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
	
	Page<Team> findByNameIgnoreCaseOrderByNameDesc(Pageable pages,String name);

}
