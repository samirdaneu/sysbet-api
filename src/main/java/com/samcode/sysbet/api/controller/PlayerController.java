package com.samcode.sysbet.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samcode.sysbet.api.entity.Player;
import com.samcode.sysbet.api.response.Response;
import com.samcode.sysbet.api.service.PlayerService;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "*")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Player>> createOrUpdate(HttpServletRequest request, @RequestBody Player player,
			BindingResult result) {
		Response<Player> response = new Response<Player>();
		try {
			validateCreatePlayer(player, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Player playerPersisted = (Player) playerService.createOrUpdate(player);
			response.setData(playerPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Player>> update(HttpServletRequest request, @RequestBody Player player,
			BindingResult result) {
		Response<Player> response = new Response<Player>();
		try {
			validateUpdatePlayer(player, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Player playerPersisted = (Player) playerService.createOrUpdate(player);
			response.setData(playerPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Player>> findById(@PathVariable("id") Integer id) {
		Response<Player> response = new Response<Player>();
		Optional<Player> player = playerService.findById(id);
		if(player == null) {
			response.getErrors().add("Register not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		} 
		response.setData(player.get());
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Integer id) {
		Response<String> response = new Response<String>();
		Optional<Player> player = playerService.findById(id);
		if(player == null) {
			response.getErrors().add("Register not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		} 
		playerService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Player>>> findAll(HttpServletRequest request, @PathVariable("page") int page,
			@PathVariable("count") int count) {
		Response<Page<Player>> response = new Response<Page<Player>>();
		Page<Player> players = null;
		players = playerService.listPlayers(page, count);
		if (players == null || players.isEmpty()) {
			response.getErrors().add("There is no player registered yet!");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(players);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}/{name}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Player>>> findByParams(HttpServletRequest request,
			@PathVariable("page") int page, @PathVariable("count") int count, @PathVariable("name") String name) {
		name = name.equals("uninformed") ? "" : name;
		Response<Page<Player>> response = new Response<Page<Player>>();
		Page<Player> players = null;
		players = playerService.findByParameters(page, count, name);
		response.setData(players);
		return ResponseEntity.ok(response);
	}
	
	private void validateCreatePlayer(Player player, BindingResult result) {
		if(player.getName() == null) {
			result.addError(new ObjectError("Player", "Name not informed!"));
			return;
		}
	}
	
	private void validateUpdatePlayer(Player player, BindingResult result) {
		if(player.getName() == null) {
			result.addError(new ObjectError("Player", "Name not informed!"));
			return;
		}
		if(player.getId() == null) {
			result.addError(new ObjectError("Player", "Id not informed!"));
			return;
		}
	}

}
