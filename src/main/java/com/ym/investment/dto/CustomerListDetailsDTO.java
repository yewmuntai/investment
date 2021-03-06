package com.ym.investment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerListDetailsDTO {
	private long id;

	private String name;
	private String email;
	private String securitiesNum;
	private String mobile;
	
	private Long created;
	private Long updated;
}
