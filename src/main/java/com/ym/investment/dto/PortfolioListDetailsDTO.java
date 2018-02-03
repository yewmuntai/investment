package com.ym.investment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortfolioListDetailsDTO {
	private long id;

	private String name;
	private int riskTolerance;
	private int returnPreference;
	private String owner;
	
	private Long created;
	private Long updated;
}
