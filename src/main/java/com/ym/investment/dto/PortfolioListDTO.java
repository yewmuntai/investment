package com.ym.investment.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortfolioListDTO {
	private List<PortfolioListDetailsDTO> list;
}
