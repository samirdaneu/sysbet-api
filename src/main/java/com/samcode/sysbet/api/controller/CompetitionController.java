package com.samcode.sysbet.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

import com.samcode.sysbet.api.entity.Competition;
import com.samcode.sysbet.api.response.Response;
import com.samcode.sysbet.api.service.CompetitionService;

@RestController
@RequestMapping("/api/competition")
@CrossOrigin(origins = "*")
public class CompetitionController {
	
	@Autowired
	private CompetitionService competitionService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Competition>> create(HttpServletRequest request, @RequestBody Competition competition, BindingResult result) {
		Response<Competition> response = new Response<Competition>();
		try {
			validateCreateCompetition(competition, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(this.competitionService.createOrUpdate(competition));
		} catch (DuplicateKeyException dke) {
			response.getErrors().add("Competition already registered!");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateCreateCompetition(Competition competition, BindingResult result) {
		if(competition.getName() == null || competition.getName().isEmpty()) {
			result.addError(new ObjectError("Competition", "Id not informed!"));
		}
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Competition>> update(HttpServletRequest request, @RequestBody Competition competition, BindingResult result) {
		Response<Competition> response = new Response<Competition>();
		try {
			validateUpdateCompetition(competition, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			response.setData(this.competitionService.createOrUpdate(competition));
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdateCompetition(Competition competition, BindingResult result) {
		if(competition.getId() == null) {
			result.addError(new ObjectError("Competition", "Id not informed"));
		}
		if(competition.getName() == null) {
			result.addError(new ObjectError("Competition", "Name not informed"));
		}
		
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Competition>> findById(@PathVariable("id") Integer id) {
		Response<Competition> response = new Response<Competition>();
		Optional<Competition> competition = competitionService.findById(id);
		if(competition == null) {
			response.getErrors().add("Register not found:" + id);
			ResponseEntity.badRequest().body(response);
		} else {
			response.setData(competitionService.findById(id).get());
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Competition>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<Competition>> response = new Response<Page<Competition>>();
		Page<Competition> competitions = competitionService.findAll(page, count);
		if(competitions == null || competitions.isEmpty()) {
			response.getErrors().add("There is no competition registered yet!");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(competitions);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Competition>> delete(@PathVariable("id") Integer id) {
		Response<Competition> response = new Response<Competition>();
		Optional<Competition> competition = this.competitionService.findById(id);
		if(competition == null) {
			response.getErrors().add("Competition not found: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		this.competitionService.delete(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}/{name}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Competition>>> findByParams(HttpServletRequest request,
			@PathVariable("page") int page, @PathVariable("count") int count, @PathVariable("name") String name) {
		name = name.equals("uninformed") ? "" : name;
		Response<Page<Competition>> response = new Response<Page<Competition>>();
		Page<Competition> competitions = null;
		competitions = competitionService.findByParameters(page, count, name);
		response.setData(competitions);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Page<Competition>>> findAllPagination(HttpServletRequest request, @PathVariable("page") int page,
			@PathVariable("count") int count) {
		Response<Page<Competition>> response = new Response<Page<Competition>>();
		Page<Competition> competitions = null;
		competitions = competitionService.listCompetitions(page, count);
		if (competitions == null || competitions.isEmpty()) {
			response.getErrors().add("There is no competition registered yet!");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(competitions);
		return ResponseEntity.ok(response);
	}
	

}
