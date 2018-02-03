package com.ym.investment.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.ym.investment.domain.Portfolio;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.dto.PortfolioListDTO;
import com.ym.investment.dto.PortfolioListDetailsDTO;

public class PortfolioAssembler {

	public static PortfolioListDTO toPortfolioListDTO(List<Portfolio> source) {
		List<PortfolioListDetailsDTO> list = source.stream().map(portfolio -> 
		toPortfolioListDetailsDTO(portfolio)
	).collect(Collectors.toList());
	
	PortfolioListDTO dto = new PortfolioListDTO();
	dto.setList(list);

	return dto;
	}

	public static PortfolioListDetailsDTO toPortfolioListDetailsDTO(Portfolio source) {
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

	public static PortfolioDetailsDTO toPortfolioDetailsDTO(Portfolio source) {
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setRiskTolerance(source.getRiskTolerance());
		dto.setReturnPreference(source.getReturnPreference());
		dto.setOwner(source.getOwner().getId());
		return dto;
	}

}
