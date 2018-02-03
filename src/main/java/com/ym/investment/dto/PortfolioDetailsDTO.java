package com.ym.investment.dto;

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
}
