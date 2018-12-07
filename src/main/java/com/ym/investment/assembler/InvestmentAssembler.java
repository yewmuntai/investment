package com.ym.investment.assembler;

import com.ym.investment.domain.Investment;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.InvestmentListDTO;
import com.ym.investment.dto.InvestmentListDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class InvestmentAssembler extends Assembler<Investment, InvestmentDetailsDTO, InvestmentListDTO, InvestmentListDetailsDTO> {

	public InvestmentListDetailsDTO toListDetailsDTO(Investment source) {
		InvestmentListDetailsDTO dto = new InvestmentListDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setReturnScore(source.getReturnScore());
		dto.setRiskScore(source.getRiskScore());
		dto.setCreated(source.getCreated().getTime());
		dto.setUpdated(source.getUpdated() == null ? null : source.getUpdated().getTime());
		return dto;
	}

	public InvestmentDetailsDTO toDetailsDTO(Investment source) {
		InvestmentDetailsDTO dto = new InvestmentDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setReturnScore(source.getReturnScore());
		dto.setRiskScore(source.getRiskScore());
		return dto;
	}

	@Override
	public InvestmentListDTO newListDTO() {
		return new InvestmentListDTO();
	}
}
