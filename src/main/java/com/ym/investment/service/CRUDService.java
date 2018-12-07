package com.ym.investment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.ym.investment.domain.BaseDomain;
import com.ym.investment.exception.NotFoundException;

/**
 * Perform CRUD operations
 *
 * @param <T1> The domain class that this controller is doing CRUD on
 * @param <T2> The DTO for details
 */
@Service
public abstract class CRUDService<T1 extends BaseDomain, T2> {
	/**
	 * Services that extend from CRUDService needs to provide the repository for database operations
	 *  
	 * @return repository that what works with T1
	 */
	abstract JpaRepository<T1, Long> getRepository();

	/**
	 * The service need to provide the class for T1 for instantiation
	 * @return The class for T1
	 */
	abstract Class<T1> getT1Class();
	
	/**
	 * To convert the T2 details DTO into the domain object 
	 * @param obj The target domain object. When it's for create, this is a new instance taken from <code>getT1Class</code>. When it's for update, this will be retrieved from the repository
	 * @param source The details DTO from the Request Body 
	 * @param params from the http call
	 */
	abstract void setDomainValues(T1 obj, T2 source, Map<String, String> params);

	/**
	 * Getting a list of domain from the repository
	 * @param params from the http call. This is not used in this default implementation.
	 * @return the List of T1
	 */
	List<T1> getList(Map<String, String> params) {
		return getRepository().findAll();
	}
	
	/**
	 * Create an instance of T1
	 * 
	 * @param param from the http call. This is not used in this default implementation.
	 * @return T1
	 */
	T1 createObj(Map<String, String> param) {
		try {
			return getT1Class().newInstance();
		}catch (Exception e) {
			throw new RuntimeException("Invalid class: " + getT1Class().getName());
		}
	}

	/**
	 * This method is called by CRUDController for the list end point
	 * 
	 * @param params from the http call
	 * @return List of T1
	 */
	public List<T1> list(Map<String, String> params) {
		List<T1> list = getList(params);
		return list;
	}

	/**
	 * This method is called by CRUDController for the get end point
	 * 
	 * @param id ID of the domain entity
	 * @param params from the http call. This is not used in this default implementation.
	 * @return the T1 domain entity
	 */
	public T1 get(Long id, Map<String, String> params) {
		return getRepository().findOne(id);
	}
	
	/**
	 * This method is called by CRUDController for the create end point
	 * 
	 * @param source The data for creating the T1 domain entity. Taken from the request body
	 * @param params from the http call.
	 * @return T1 domain entity created
	 */
	public T1 create(T2 source, Map<String, String> params) {
		T1 obj = createObj(params);
		setDomainValues(obj, source, params);
		obj.setCreated(new Date());
		return getRepository().save(obj);
	}

	/**
	 * This method is called by CRUDController for the update end point
	 * 
	 * @param id ID of the domain entity for updating
	 * @param source The data for creating the T1 domain entity. Taken from the request body
	 * @param params from the http call.
	 * @return T1 domain entity updated
	 */
	public T1 update(Long id, T2 source, Map<String, String> params) {
		T1 obj = getRepository().findOne(id);
		if (obj == null) {
			throw new NotFoundException(getT1Class().getName() + " (" + id + ") do not exists");
		}
		setDomainValues(obj, source, params);
		obj.setUpdated(new Date());
		
		return getRepository().save(obj);
	}
	
	/**
	 * This method is called by CRUDController for the delete end point
	 * 
	 * @param id ID of the domain entity for deleting
	 * @param params from the http call. This is not used in this default implementation.
	 * @return T1 domain entity deleted
	 */
	public T1 delete(long id, Map<String, String> params) {
		T1 obj = getRepository().getOne(id);
		if (obj == null) {
			throw new NotFoundException(getT1Class().getName() + " (" + id + ") do not exists");
		}
		getRepository().delete(obj);
		
		return obj;
	}
}
