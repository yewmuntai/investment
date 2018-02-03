package com.ym.investment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDetailsDTO {
	private long id;

	private String name;
	private String email;
	private String securitiesNum;
	private String mobile;
	
	private int riskTolerance;
	private int returnPreference;
}
