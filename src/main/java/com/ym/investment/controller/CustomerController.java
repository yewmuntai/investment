package com.ym.investment.controller;

import java.util.List;
import java.util.Map;

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
public class CustomerController extends CRUDController<Customer, CustomerDetailsDTO, CustomerListDTO> {
	@Autowired
	private CustomerService classService;
	
	@Override
	CustomerService getService() {
		return classService;
	}

	@Override
	CustomerListDTO toListDTO(List<Customer> source, Map<String, String> params) {
		return CustomerAssembler.toCustomerListDTO(source);
	}

	/**
	 * Convert Customer domain entity to CustomerDetailsDTO
	 * 
	 * @param source The Customer entity retrieved
	 * @param params parameters from the http call. May contain the following 2 params:
	 * includePortfolio - if this is present, portfolio data will be included in the response
	 * includeRecommendations - if this is present, recommendations data will be included in the portfolio data
	 * 
	 * @return CustomerDetailsDTO
	 */
	@Override
	CustomerDetailsDTO toDetailsDTO(Customer source, Map<String, String> params) {
		CustomerDetailsDTO dto = CustomerAssembler.toCustomerDetailsDTO(source);
		
		if (params != null && params.containsKey("includePortfolio")) {
			CustomerAssembler.insertPortfolio(dto, params.containsKey("includeRecommendations"));
		}
		
		return dto;
	}
}
