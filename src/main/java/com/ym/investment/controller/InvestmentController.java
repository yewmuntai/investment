package com.ym.investment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ym.investment.assembler.InvestmentAssembler;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.InvestmentListDTO;
import com.ym.investment.domain.Investment;
import com.ym.investment.service.InvestmentService;

@RestController
@RequestMapping("/api/investment")
@CrossOrigin(origins = "http://localhost:8081")
public class InvestmentController extends CRUDController<Investment, InvestmentDetailsDTO, InvestmentListDTO> {
	@Autowired
	private InvestmentService investmentService;
	
	@Override
	InvestmentService getService() {
		return investmentService;
	}

	@Override
	InvestmentListDTO toListDTO(List<Investment> source) {
		return InvestmentAssembler.toInvestmentListDTO(source);
	}

	@Override
	InvestmentDetailsDTO toDetailsDTO(Investment source) {
		return InvestmentAssembler.toInvestmentDetailsDTO(source);
	}
}