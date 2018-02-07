package com.ym.investment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ym.investment.domain.BaseDomain;
import com.ym.investment.service.CRUDService;

/**
 * Base class for controllers. Implementation for CRUD actions are included here.
 * The whole ecosystem involved this controller, a CRUD service, a domain class, a DTO for list, and a DTO for details..
 * CRUD controller is where the end points are.
 * The CRUD service is the CRUD operations are performed.
 * DTO are POJO that meant to send as response or as Request Body.
 * 
 * @param <T1> The domain class that this controller is doing CRUD on
 * @param <T2> The DTO for details
 * @param <T3> The DTO for list
 */
public abstract class CRUDController<T1 extends BaseDomain, T2, T3> {

	/**
	 * Controllers that extend from CRUDController needs to provide the CRUDService
	 *  
	 * @return CRUDService that what works with T1
	 */
	abstract CRUDService<T1, T2> getService();

	/**
	 * Implementation for converting a list of T1 into the T3 dto
	 * 
	 * @param source List of T1. This is taken from the CRUDService list method
	 * @param params from the http call
	 * @return T3 DTO for list action
	 */
	abstract T3 toListDTO(List<T1> source, Map<String, String> params);

	/**
	 * Implementation for converting a domain object into a T2 DTO.
	 * 
	 * @param source the domain object. Retried from CRUDService
	 * @param params from the http call
	 * @return
	 */
	abstract T2 toDetailsDTO(T1 source, Map<String, String> params);
	
	/**
	 * The end point for list
	 * 
	 * @param params from the http call.
	 * @return the ResponseEntity that contains the list DTO
	 */
	@GetMapping
	public ResponseEntity<T3> list(@RequestParam Map<String, String> params) {
		List<T1> classes = getService().list(params);
		T3 dto = toListDTO(classes, params);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * To retrieve the domain object based on the id.
	 * 
	 * @param id of the domain object. This is a path variable
	 * @param params from the http call
	 * @return the ResponseEntity that contains the details DTO of the domain retrieved
	 */
	@GetMapping("/{id}")
	public ResponseEntity<T2> get(@PathVariable("id") Long id, @RequestParam Map<String, String> params) {
		T1 obj = getService().get(id);
		T2 dto = toDetailsDTO(obj, params);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * To create and save a new domain entity
	 * 
	 * @param dto The data needed to create the domain entity. This is the Request Body
	 * @param params from the http call
	 * @return The ResponseEntity that contains the details DTO of the domain created
	 */
	@PostMapping
	public ResponseEntity<T2> create(@RequestBody T2 dto, @RequestParam Map<String, String> params) {
		T1 obj = getService().create(dto);
		T2 result = toDetailsDTO(obj, params);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	/**
	 * To update and save a new domain entity
	 * 
	 * @param id of the domain object to be updated. This is a path variable
	 * @param dto The data needed to update the domain entity. This is the Request Body
	 * @param params from the http call
	 * @return The ResponseEntity that contains the details DTO of the domain updated
	 */
	@PutMapping("/{id}")
	public ResponseEntity<T2> update(@PathVariable("id") Long id, @RequestBody T2 dto) {
		T1 obj = getService().update(id, dto);
		T2 result = toDetailsDTO(obj, null);
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	/**
	 * To delete a domain entity
	 * 
	 * @param id of the domain object to be deleted. This is a path variable
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<T2> delete(@PathVariable("id") Long id) {
		T1 obj = getService().delete(id);
		T2 result = toDetailsDTO(obj, null);
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
}
