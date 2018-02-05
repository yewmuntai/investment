package com.ym.investment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ym.investment.assembler.InvestmentAssembler;
import com.ym.investment.assembler.PortfolioAssembler;
import com.ym.investment.dto.InvestmentListDTO;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.dto.PortfolioListDTO;
import com.ym.investment.domain.Investment;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.service.InvestmentService;
import com.ym.investment.service.PortfolioService;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "http://localhost:8081")
public class PortfolioController extends CRUDController<Portfolio, PortfolioDetailsDTO, PortfolioListDTO> {
	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private InvestmentService investmentService;
	
	@Override
	PortfolioService getService() {
		return portfolioService;
	}

	@Override
	PortfolioListDTO toListDTO(List<Portfolio> source) {
		return PortfolioAssembler.toPortfolioListDTO(source);
	}

	@Override
	PortfolioDetailsDTO toDetailsDTO(Portfolio source, Map<String, String> params) {
		PortfolioDetailsDTO dto = PortfolioAssembler.toPortfolioDetailsDTO(source);
		
		if (params != null && params.containsKey("includeRecommendations")) {
			PortfolioAssembler.insertRecommendations(dto);
		}

		return dto;
	}
	
	@GetMapping("/{id}/recommend")
	public ResponseEntity<InvestmentListDTO> recommend(@PathVariable("id") long id) {
		Portfolio portfolio = portfolioService.get(id);
		List<Investment> list = investmentService.recommend(portfolio.getRiskTolerance(), portfolio.getReturnPreference());
		InvestmentListDTO dto = InvestmentAssembler.toInvestmentListDTO(list);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
