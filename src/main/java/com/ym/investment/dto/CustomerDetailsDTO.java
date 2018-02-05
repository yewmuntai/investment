package com.ym.investment.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<PortfolioDetailsDTO> portfolios;	
}
