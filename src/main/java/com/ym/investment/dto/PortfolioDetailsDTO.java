package com.ym.investment.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortfolioDetailsDTO {
	private long id;

	private String name;
	private int riskTolerance;
	private int returnPreference;
	private long owner;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<InvestmentDetailsDTO> recommendations;
}
