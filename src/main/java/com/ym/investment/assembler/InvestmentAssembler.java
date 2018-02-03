package com.ym.investment.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.ym.investment.domain.Investment;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.InvestmentListDTO;
import com.ym.investment.dto.InvestmentListDetailsDTO;

public class InvestmentAssembler {

	public static InvestmentListDTO toInvestmentListDTO(List<Investment> source) {
		List<InvestmentListDetailsDTO> list = source.stream().map(investment -> 
		toInvestmentListDetailsDTO(investment)
	).collect(Collectors.toList());
	
	InvestmentListDTO dto = new InvestmentListDTO();
	dto.setList(list);

	return dto;
	}

	public static InvestmentListDetailsDTO toInvestmentListDetailsDTO(Investment source) {
		InvestmentListDetailsDTO dto = new InvestmentListDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setReturnScore(source.getReturnScore());
		dto.setRiskScore(source.getRiskScore());
		dto.setCreated(source.getCreated().getTime());
		dto.setUpdated(source.getUpdated() == null ? null : source.getUpdated().getTime());
		return dto;
	}

	public static InvestmentDetailsDTO toInvestmentDetailsDTO(Investment source) {
		InvestmentDetailsDTO dto = new InvestmentDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setReturnScore(source.getReturnScore());
		dto.setRiskScore(source.getRiskScore());
		return dto;
	}

}
