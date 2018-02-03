package com.ym.investment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvestmentDetailsDTO {
	private long id;

	private String name;
	private int riskScore;
	private int returnScore;
}
