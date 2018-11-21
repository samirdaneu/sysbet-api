package com.samcode.sysbet.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.samcode.sysbet.api.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
	
	Page<Player> findByNameIgnoreCaseOrderByNameDesc(Pageable pages,String name);

}
