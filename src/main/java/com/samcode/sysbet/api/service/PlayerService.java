package com.samcode.sysbet.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.samcode.sysbet.api.entity.Player;

@Component
public interface PlayerService {
	
	Player createOrUpdate(Player player);
	
	Optional<Player> findById(Integer id);
	
	void delete(Integer id);
	
	Iterable<Player> findAll();
	
	Page<Player> listPlayers(int page, int count);
	
	Page<Player> findByParameters(int page, int count, String name);

}
