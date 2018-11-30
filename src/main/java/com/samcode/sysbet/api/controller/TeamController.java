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

import com.samcode.sysbet.api.entity.Team;
import com.samcode.sysbet.api.response.Response;
import com.samcode.sysbet.api.service.TeamService;

@RestController
@RequestMapping("/api/team")
@CrossOrigin(origins = "*")
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Team>> createOrUpdate(HttpServletRequest request, @RequestBody Team team,
			BindingResult result) {
		Response<Team> response = new Response<Team>();
		try {
			validateCreateTeam(team, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Team teamPersisted = (Team) teamService.createOrUpdate(team);
			response.setData(teamPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Team>> update(HttpServletRequest request, @RequestBody Team team,
			BindingResult result) {
		Response<Team> response = new Response<Team>();
		try {
			validateUpdateTeam(team, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Team teamPersisted = (Team) teamService.createOrUpdate(team);
			response.setData(teamPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Team>> findById(@PathVariable("id") Integer id) {
		Response<Team> response = new Response<Team>();
		Optional<Team> team = teamService.findById(id);
		if(team == null) {
			response.getErrors().add("Register not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		} 
		response.setData(team.get());
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Integer id) {
		Response<String> response = new Response<String>();
		Optional<Team> team = teamService.findById(id);
		if(team == null) {
			response.getErrors().add("Register not found id: " + id);
			return ResponseEntity.badRequest().body(response);
		} 
		teamService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Team>>> findAllPagination(HttpServletRequest request, @PathVariable("page") int page,
			@PathVariable("count") int count) {
		Response<Page<Team>> response = new Response<Page<Team>>();
		Page<Team> teams = null;
		teams = teamService.listTeams(page, count);
		if (teams == null || teams.isEmpty()) {
			response.getErrors().add("There is no team registered yet!");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(teams);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Iterable<Team>>> findAll(HttpServletRequest request) {
		Response<Iterable<Team>> response = new Response<Iterable<Team>>();
		Iterable<Team> teams = null;
		teams = teamService.findAll();
		if (teams == null) {
			response.getErrors().add("There is no team registered yet!");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(teams);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}/{name}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Team>>> findByParams(HttpServletRequest request,
			@PathVariable("page") int page, @PathVariable("count") int count, @PathVariable("name") String name) {
		name = name.equals("uninformed") ? "" : name;
		Response<Page<Team>> response = new Response<Page<Team>>();
		Page<Team> teams = null;
		teams = teamService.findByParameters(page, count, name);
		response.setData(teams);
		return ResponseEntity.ok(response);
	}
	
	private void validateCreateTeam(Team team, BindingResult result) {
		if(team.getName() == null) {
			result.addError(new ObjectError("Team", "Name not informed!"));
			return;
		}
	}
	
	private void validateUpdateTeam(Team team, BindingResult result) {
		if(team.getName() == null) {
			result.addError(new ObjectError("Team", "Name not informed!"));
			return;
		}
		if(team.getId() == null) {
			result.addError(new ObjectError("Team", "Id not informed!"));
			return;
		}
	}

}
