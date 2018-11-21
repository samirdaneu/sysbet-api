package com.samcode.sysbet.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samcode.sysbet.api.entity.Player;
import com.samcode.sysbet.api.repository.PlayerRepository;
import com.samcode.sysbet.api.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public Player createOrUpdate(Player player) {
		return this.playerRepository.save(player);
	}

	@Override
	public Optional<Player> findById(Integer id) {
		return this.playerRepository.findById(id);
	}

	@Override
	public void delete(Integer id) {
		this.playerRepository.deleteById(id);
	}

	@Override
	public Iterable<Player> findAll() {
		return this.playerRepository.findAll();
	}

	@Override
	public Page<Player> listPlayers(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.playerRepository.findAll(pages);
	}

	@Override
	public Page<Player> findByParameters(int page, int count, String name) {
		Pageable pages = PageRequest.of(page, count);
		return this.playerRepository.findByNameIgnoreCaseOrderByNameDesc(pages, name);
	}

}
