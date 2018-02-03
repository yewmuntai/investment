package com.ym.investment.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvestmentListDTO {
	private List<InvestmentListDetailsDTO> list;
}
