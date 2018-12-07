package com.ym.investment.controller;

import java.util.List;
import java.util.Map;

import com.ym.investment.dto.CustomerListDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ym.investment.assembler.CustomerAssembler;
import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.dto.CustomerListDTO;
import com.ym.investment.domain.Customer;
import com.ym.investment.service.CustomerService;

/**
 * This controller do CRUD operations on customer domain
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController extends CRUDController<Customer, CustomerDetailsDTO, CustomerListDTO, CustomerListDetailsDTO> {
	@Autowired
	private CustomerService classService;
	@Autowired
	private CustomerAssembler customerAssembler;
	
	@Override
	CustomerService getService() {
		return classService;
	}

	@Override
	CustomerAssembler getAssembler() {
		return customerAssembler;
	}

	/**
	 * populate portfolio dto.
	 *
	 * @param dto
	 * @param params parameters from the http call. May contain the following 2 params:
	 * includePortfolio - if this is present, portfolio data will be included in the response
	 * includeRecommendations - if this is present, recommendations data will be included in the portfolio data
	 */
	@Override
	void processDetailsDTOChildren(CustomerDetailsDTO dto, Map<String, String> params) {
		if (params != null && params.containsKey("includePortfolio")) {
			customerAssembler.insertPortfolio(dto, params.containsKey("includeRecommendations"));
		}
	}
}
