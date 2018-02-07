package com.ym.investment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.domain.Investment;
import com.ym.investment.repository.InvestmentRepository;

@Service
public class InvestmentService extends CRUDService<Investment, InvestmentDetailsDTO>{
	@Autowired
	private InvestmentRepository investmentRepository;
	
	@Override
	InvestmentRepository getRepository() {
		return investmentRepository;
	}

	@Override
	void setDomainValues(Investment investment, InvestmentDetailsDTO source) {
		investment.setName(source.getName());
		investment.setReturnScore(source.getReturnScore());
		investment.setRiskScore(source.getRiskScore());
	}

	@Override
	java.lang.Class<Investment> getT1Class() {
		return Investment.class;
	}
	
	void setRepository(InvestmentRepository repo) {
		if (investmentRepository == null) {
			investmentRepository = repo;
		}
	}
	
	public List<Investment> recommend(int riskScore, int returnScore) {
		return investmentRepository.findByRiskScoreOrReturnScore(riskScore, returnScore);
	}
}
