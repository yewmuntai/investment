package com.ym.investment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ym.investment.service.CRUDService;

public abstract class CRUDController<T1, T2, T3> {
	abstract CRUDService<T1, T2> getService();
	abstract T3 toListDTO(List<T1> source);
	abstract T2 toDetailsDTO(T1 source);
	
	@GetMapping
	public ResponseEntity<T3> list(@RequestParam(value="parent")Optional<Long> parentId) {
		List<T1> classes = getService().list(parentId);
		T3 dto = toListDTO(classes);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<T2> get(@PathVariable("id") Long id) {
		T1 obj = getService().get(id);
		T2 dto = toDetailsDTO(obj);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<T2> create(@RequestBody T2 dto) {
		T1 obj = getService().create(dto);
		T2 result = toDetailsDTO(obj);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<T2> update(@PathVariable("id") Long id, @RequestBody T2 dto) {
		T1 obj = getService().update(id, dto);
		T2 result = toDetailsDTO(obj);
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<T2> delete(@PathVariable("id") Long id) {
		T1 obj = getService().delete(id);
		T2 result = toDetailsDTO(obj);
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
}
