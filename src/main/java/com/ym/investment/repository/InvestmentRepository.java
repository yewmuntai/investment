package com.ym.investment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ym.investment.domain.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, Long>{
	
}
