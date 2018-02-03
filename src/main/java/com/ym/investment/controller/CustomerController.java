package com.ym.investment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ym.investment.assembler.CustomerAssembler;
import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.dto.CustomerListDTO;
import com.ym.investment.domain.Customer;
import com.ym.investment.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:8081")
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
	CustomerDetailsDTO toDetailsDTO(Customer source) {
		return CustomerAssembler.toCustomerDetailsDTO(source);
	}
}
