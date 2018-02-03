package com.ym.investment.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.ym.investment.domain.Customer;
import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.dto.CustomerListDTO;
import com.ym.investment.dto.CustomerListDetailsDTO;

public class CustomerAssembler {

	public static CustomerListDTO toCustomerListDTO(List<Customer> source) {
		List<CustomerListDetailsDTO> list = source.stream().map(investment -> 
		toCustomerListDetailsDTO(investment)
	).collect(Collectors.toList());
	
	CustomerListDTO dto = new CustomerListDTO();
	dto.setList(list);

	return dto;
	}

	public static CustomerListDetailsDTO toCustomerListDetailsDTO(Customer source) {
		CustomerListDetailsDTO dto = new CustomerListDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setEmail(source.getEmail());
		dto.setMobile(source.getMobile());
		dto.setSecuritiesNum(source.getSecuritiesNum());
		dto.setCreated(source.getCreated().getTime());
		dto.setUpdated(source.getUpdated() == null ? null : source.getUpdated().getTime());
		return dto;
	}

	public static CustomerDetailsDTO toCustomerDetailsDTO(Customer source) {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();
		dto.setId(source.getId());
		dto.setName(source.getName());
		dto.setEmail(source.getEmail());
		dto.setMobile(source.getMobile());
		dto.setSecuritiesNum(source.getSecuritiesNum());
		return dto;
	}

}
