package com.ym.investment.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.ym.investment.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ym.investment.domain.Investment;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.dto.PortfolioListDTO;
import com.ym.investment.dto.PortfolioListDetailsDTO;
import com.ym.investment.service.InvestmentService;

@Component
public class PortfolioAssembler extends Assembler<Portfolio, PortfolioDetailsDTO, PortfolioListDTO, PortfolioListDetailsDTO> {
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private InvestmentAssembler investmentAssembler;
	
	public PortfolioListDetailsDTO toListDetailsDTO(Portfolio source) {
		PortfolioListDetailsDTO dto = new PortfolioListDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setRiskTolerance(source.getRiskTolerance());
		dto.setReturnPreference(source.getReturnPreference());
		dto.setOwner(source.getOwner().getName());
		dto.setCreated(source.getCreated().getTime());
		dto.setUpdated(source.getUpdated() == null ? null : source.getUpdated().getTime());
		return dto;
	}

	public PortfolioDetailsDTO toDetailsDTO(Portfolio source) {
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setRiskTolerance(source.getRiskTolerance());
		dto.setReturnPreference(source.getReturnPreference());
		dto.setOwner(source.getOwner().getId());
		return dto;
	}

	@Override
	public PortfolioListDTO newListDTO() {
		return new PortfolioListDTO();
	}

	public void insertRecommendations(PortfolioDetailsDTO dto) {
		List<Investment> list = investmentService.recommend(dto.getRiskTolerance(), dto.getReturnPreference());
		List<InvestmentDetailsDTO> recommendations = list.stream().map(investment ->
			investmentAssembler.toDetailsDTO(investment)
		).collect(Collectors.toList());

		dto.setRecommendations(recommendations);
	}

	/**
	 * This is for unit test.
	 *
	 * @param investmentService investmentService
	 */
	public void setInvestmentService(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	/**
	 * This is for unit test.
	 *
	 * @param investmentAssembler InvestmentAssembler
	 */
	public void setInvestmentAssembler(InvestmentAssembler investmentAssembler) {
		this.investmentAssembler = investmentAssembler;
	}
}
