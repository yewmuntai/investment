package com.ym.investment.controller;

import java.util.List;
import java.util.Map;

import com.ym.investment.assembler.Assembler;
import com.ym.investment.dto.PortfolioListDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PortfolioController extends CRUDController<Portfolio, PortfolioDetailsDTO, PortfolioListDTO, PortfolioListDetailsDTO> {
	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private PortfolioAssembler portfolioAssembler;
	@Autowired
	private InvestmentAssembler investmentAssembler;

	@Override
	PortfolioService getService() {
		return portfolioService;
	}

	@Override
	PortfolioAssembler getAssembler() {
		return portfolioAssembler;
	}

	/**
	 * populate recommendation dto.
	 *
	 * @param dto
	 * @param params parameters from the http call. May contain the following 2 params:
	 * includePortfolio - if this is present, portfolio data will be included in the response
	 * includeRecommendations - if this is present, recommendations data will be included in the portfolio data
	 */
	@Override
	void processDetailsDTOChildren(PortfolioDetailsDTO dto, Map<String, String> params) {
		if (params != null && params.containsKey("includeRecommendations")) {
			portfolioAssembler.insertRecommendations(dto);
		}
	}

	/**
	 * Get a list of recommended investments for this portfolio.
	 * 
	 * @param id The ID for the portfolio. This is a path variable.
	 * @return InvestmentListDTO
	 */
	@GetMapping("/{id}/recommend")
	public ResponseEntity<InvestmentListDTO> recommend(@PathVariable("id") long id) {
		Portfolio portfolio = portfolioService.get(id, null);
		List<Investment> list = investmentService.recommend(portfolio.getRiskTolerance(), portfolio.getReturnPreference());
		InvestmentListDTO dto = investmentAssembler.toListDTO(list);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
