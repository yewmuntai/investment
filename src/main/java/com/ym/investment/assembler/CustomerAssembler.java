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
public class CustomerAssembler {
	private static PortfolioService portfolioService;

	@Autowired
	public void setPortfolioService(PortfolioService portfolioService) {
		CustomerAssembler.portfolioService = portfolioService;
	}

	public static CustomerListDTO toCustomerListDTO(List<Customer> source) {
		List<CustomerListDetailsDTO> list = source.stream().map(investment -> 
		toCustomerListDetailsDTO(investment)
	).collect(Collectors.toList());

	CustomerListDTO dto = new CustomerListDTO();
	dto.setList(list);

	return dto;
	}

	public static CustomerListDetailsDTO toCustomerListDetailsDTO(Customer source) {
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

	public static CustomerDetailsDTO toCustomerDetailsDTO(Customer source) {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setEmail(source.getEmail());
		dto.setMobile(source.getMobile());
		dto.setSecuritiesNum(source.getSecuritiesNum());
		return dto;
	}

	public static void insertPortfolio(CustomerDetailsDTO dto, boolean includeRecommendations) {
		Map<String, String> params = new HashMap<>();
		params.put("parent", dto.getId() + "");
		System.out.println(portfolioService);
		List<Portfolio> portfolios = portfolioService.list(params);
		
		List<PortfolioDetailsDTO> pDTOs = portfolios.stream().map(portfolio -> {
			PortfolioDetailsDTO pDTO = PortfolioAssembler.toPortfolioDetailsDTO(portfolio);
			
			if (includeRecommendations) {
				PortfolioAssembler.insertRecommendations(pDTO);
			}
			
			return pDTO;
		}).collect(Collectors.toList()); 
		dto.setPortfolios(pDTOs);
	}
}
