package com.ym.investment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.domain.Customer;
import com.ym.investment.repository.CustomerRepository;

@Service
public class CustomerService extends CRUDService<Customer, CustomerDetailsDTO>{
	@Autowired
	private CustomerRepository CustomerRepository;
	
	@Override
	CustomerRepository getRepository() {
		return CustomerRepository;
	}

	@Override
	void setDomainValues(Customer customer, CustomerDetailsDTO source) {
		customer.setName(source.getName());
		customer.setMobile(source.getMobile());
		customer.setSecuritiesNum(source.getSecuritiesNum());
		customer.setEmail(source.getEmail());
	}

	@Override
	java.lang.Class<Customer> getT1Class() {
		return Customer.class;
	}
	
	public void setRepository(CustomerRepository repo) {
		if (CustomerRepository == null) {
			CustomerRepository = repo;
		}
	}
}
