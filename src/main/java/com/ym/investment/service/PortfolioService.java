package com.ym.investment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.exception.InvestmentException;
import com.ym.investment.domain.Customer;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.repository.PortfolioRepository;

@Service
public class PortfolioService extends CRUDService<Portfolio, PortfolioDetailsDTO>{
	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	private CustomerService customerService;
	
	@Override
	PortfolioRepository getRepository() {
		return portfolioRepository;
	}

	@Override
	void setDomainValues(Portfolio portfolio, PortfolioDetailsDTO source) {
		portfolio.setName(source.getName());
		portfolio.setRiskTolerance(source.getRiskTolerance());
		portfolio.setReturnPreference(source.getReturnPreference());
		
		Customer owner = customerService.get(source.getOwner());
		if (owner == null) {
			throw new InvestmentException("Invalid owner");
		}
		
		portfolio.setOwner(owner);
	}

	@Override
	java.lang.Class<Portfolio> getT1Class() {
		return Portfolio.class;
	}
	
	@Override
	List<Portfolio> getListByParent(long parentId) {
		Customer owner = customerService.get(parentId);
		return portfolioRepository.findByOwner(owner);
	}
	
	public void setRepository(PortfolioRepository repo) {
		if (portfolioRepository == null) {
			portfolioRepository = repo;
		}
	}
}
