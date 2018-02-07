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
	CustomerListDTO toListDTO(List<Customer> source) {
		return CustomerAssembler.toCustomerListDTO(source);
	}

	@Override
	CustomerDetailsDTO toDetailsDTO(Customer source, Map<String, String> params) {
		CustomerDetailsDTO dto = CustomerAssembler.toCustomerDetailsDTO(source);
		
		if (params != null && params.containsKey("includePortfolio")) {
			CustomerAssembler.insertPortfolio(dto, params.containsKey("includeRecommendations"));
		}
		
		return dto;
	}
}
