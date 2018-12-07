package com.ym.investment.assembler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ym.investment.domain.Customer;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.dto.CustomerListDTO;
import com.ym.investment.dto.CustomerListDetailsDTO;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.service.PortfolioService;

@Component
public class CustomerAssembler extends Assembler<Customer, CustomerDetailsDTO, CustomerListDTO, CustomerListDetailsDTO> {
	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private PortfolioAssembler portfolioAssembler;

	public CustomerListDetailsDTO toListDetailsDTO(Customer source) {
		CustomerListDetailsDTO dto = new CustomerListDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setEmail(source.getEmail());
		dto.setMobile(source.getMobile());
		dto.setSecuritiesNum(source.getSecuritiesNum());
		dto.setCreated(source.getCreated().getTime());
		dto.setUpdated(source.getUpdated() == null ? null : source.getUpdated().getTime());
		return dto;
	}

	public CustomerDetailsDTO toDetailsDTO(Customer source) {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setEmail(source.getEmail());
		dto.setMobile(source.getMobile());
		dto.setSecuritiesNum(source.getSecuritiesNum());
		return dto;
	}

	@Override
	public CustomerListDTO newListDTO() {
		return new CustomerListDTO();
	}

	public void insertPortfolio(CustomerDetailsDTO dto, boolean includeRecommendations) {
		Map<String, String> params = new HashMap<>();
		params.put("parent", dto.getId() + "");
		List<Portfolio> portfolios = portfolioService.list(params);
		
		List<PortfolioDetailsDTO> pDTOs = portfolios.stream().map(portfolio -> {
			PortfolioDetailsDTO pDTO = portfolioAssembler.toDetailsDTO(portfolio);
			
			if (includeRecommendations) {
				portfolioAssembler.insertRecommendations(pDTO);
			}
			
			return pDTO;
		}).collect(Collectors.toList()); 
		dto.setPortfolios(pDTOs);
	}

	/**
	 * This is for unit test.
	 *
	 * @param portfolioService PortfolioService
	 */
	public void setPortfolioService(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	/**
	 * This is for unit test.
	 *
	 * @param portfolioAssembler portfolioAssembler
	 */
	public void setPortfolioAssembler(PortfolioAssembler portfolioAssembler) {
		this.portfolioAssembler = portfolioAssembler;
	}
}
