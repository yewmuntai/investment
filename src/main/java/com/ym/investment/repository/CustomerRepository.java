package com.ym.investment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ym.investment.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
}
