package com.ym.investment.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.exception.InvestmentException;
import com.ym.investment.domain.Customer;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.repository.PortfolioRepository;

/**
 * CRUD service for portfolio
 *
 */
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
	void setDomainValues(Portfolio portfolio, PortfolioDetailsDTO source, Map<String, String> params) {
		portfolio.setName(source.getName());
		portfolio.setRiskTolerance(source.getRiskTolerance());
		portfolio.setReturnPreference(source.getReturnPreference());
		
		Customer owner = customerService.get(source.getOwner(), null);
		if (owner == null) {
			throw new InvestmentException("Invalid owner");
		}
		
		portfolio.setOwner(owner);
	}

	@Override
	java.lang.Class<Portfolio> getT1Class() {
		return Portfolio.class;
	}
	
	/**
	 * Getting a list of Portfolio for the CRUD list operation.
	 * 
	 * @param params May contain the parameter <code>parent</code>.
	 * If <code>parent</code> is present, will only return portfolio that the owner with id of <code>parent</code>.
	 * If <code>parent</code> is not present, all portfolio will be returned. 
	 */
	@Override
	List<Portfolio> getList(Map<String, String> params) {
		List<Portfolio> list = null;

		try {
			long parentId = Long.parseLong(params.get("parent"));
			Customer owner = customerService.get(parentId, null);
			list = portfolioRepository.findByOwner(owner);
		}catch (Exception e) {
			list = getRepository().findAll();
		}

		return list;
	}
	
	/**
	 * For unit test
	 * @param repo InvestmentRepository
	 */
	void setRepository(PortfolioRepository repo) {
		if (portfolioRepository == null) {
			portfolioRepository = repo;
		}
	}
	
	/**
	 * For unit test
	 * @param repo CustomerService. This should be mocked.
	 */
	void setCustomerService(CustomerService service) {
		if (this.customerService == null) {
			customerService = service;
		}
	}
}
