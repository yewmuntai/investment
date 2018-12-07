package com.ym.investment.controller;

import com.ym.investment.dto.InvestmentListDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ym.investment.assembler.InvestmentAssembler;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.InvestmentListDTO;
import com.ym.investment.domain.Investment;
import com.ym.investment.service.InvestmentService;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController extends CRUDController<Investment, InvestmentDetailsDTO, InvestmentListDTO, InvestmentListDetailsDTO> {
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private InvestmentAssembler investmentAssembler;
	
	@Override
	InvestmentService getService() {
		return investmentService;
	}

	@Override
	InvestmentAssembler getAssembler() {
		return investmentAssembler;
	}

}
