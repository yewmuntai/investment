package com.ym.investment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ym.investment.domain.Customer;
import com.ym.investment.domain.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{
	public List<Portfolio> findByOwner(Customer customer);
}
