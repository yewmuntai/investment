package com.ym.investment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ym.investment.domain.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, Long>{
	public List<Investment> findByRiskScoreOrReturnScore(int riskScore, int returnScore);
}
