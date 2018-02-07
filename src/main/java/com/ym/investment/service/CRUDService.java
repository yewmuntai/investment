package com.ym.investment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.ym.investment.domain.BaseDomain;
import com.ym.investment.exception.NotFoundException;

@Service
public abstract class CRUDService<T1 extends BaseDomain, T2> {
	abstract JpaRepository<T1, Long> getRepository();
	abstract Class<T1> getT1Class();
	abstract void setDomainValues(T1 obj, T2 source);
	
	List<T1> getList(Map<String, String> params) {
		return getRepository().findAll();
	}
	
	T1 createObj() {
		try {
			return getT1Class().newInstance();
		}catch (Exception e) {
			throw new RuntimeException("Invalid class: " + getT1Class().getName());
		}
	}

	public List<T1> list(Map<String, String> params) {
		List<T1> list = getList(params);
		return list;
	}

	public T1 get(Long id) {
		return getRepository().findOne(id);
	}
	
	public T1 create(T2 source) {
		T1 obj = createObj();
		setDomainValues(obj, source);
		obj.setCreated(new Date());
		return getRepository().save(obj);
	}

	public T1 update(Long id, T2 source) {
		T1 obj = getRepository().findOne(id);
		if (obj == null) {
			throw new NotFoundException(getT1Class().getName() + " (" + id + ") do not exists");
		}
		setDomainValues(obj, source);
		obj.setUpdated(new Date());
		
		return getRepository().save(obj);
	}
	
	public T1 delete(long id) {
		T1 obj = getRepository().getOne(id);
		if (obj == null) {
			throw new NotFoundException(getT1Class().getName() + " (" + id + ") do not exists");
		}
		getRepository().delete(obj);
		
		return obj;
	}
}
