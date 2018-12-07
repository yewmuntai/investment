package com.ym.investment.assembler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ym.investment.domain.Investment;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.InvestmentListDTO;
import com.ym.investment.dto.InvestmentListDetailsDTO;

public class InvestmentAssemblerTest {
	private Investment testInstance1;
	private Investment testInstance2;
	private Investment testInstance3;

	private InvestmentAssembler investmentAssembler = new InvestmentAssembler();

	@Before
	public void init() {
		testInstance1 = makeObj(true);
		testInstance2 = makeObj(true);
		testInstance3 = makeObj(false);
	}
	
	private Investment makeObj(boolean setUpdateDate) {
		Investment obj = new Investment();
		long id = (long)(Math.random() * 1000);
		obj.setId(id);
		obj.setName("Test Investment " + id);
		obj.setReturnScore((int)(Math.random() * 10));
		obj.setRiskScore((int)(Math.random() * 10));
		obj.setCreated(new Date(System.currentTimeMillis()));
		if (setUpdateDate) {
			obj.setUpdated(new Date(System.currentTimeMillis()));
		}
		return obj;
	}

	@Test
	public void toInvestmentDetails() {
		InvestmentDetailsDTO dto = investmentAssembler.toDetailsDTO(testInstance1);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance1.getId());
		assertEquals(dto.getName(), testInstance1.getName());
		assertEquals(dto.getReturnScore(), testInstance1.getReturnScore());
		assertEquals(dto.getRiskScore(), testInstance1.getRiskScore());
	}

	@Test
	public void toInvestmentListDetailsWithUpdateDate() {
		InvestmentListDetailsDTO dto = investmentAssembler.toListDetailsDTO(testInstance1);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance1.getId());
		assertEquals(dto.getName(), testInstance1.getName());
		assertEquals(dto.getReturnScore(), testInstance1.getReturnScore());
		assertEquals(dto.getRiskScore(), testInstance1.getRiskScore());
		assertEquals(dto.getCreated(), new Long(testInstance1.getCreated().getTime()));
		assertEquals(dto.getUpdated(), new Long(testInstance1.getUpdated().getTime()));
	}
	
	@Test
	public void toInvestmentListDetailsWithoutUpdateDate() {
		InvestmentListDetailsDTO dto = investmentAssembler.toListDetailsDTO(testInstance3);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance3.getId());
		assertEquals(dto.getName(), testInstance3.getName());
		assertEquals(dto.getReturnScore(), testInstance3.getReturnScore());
		assertEquals(dto.getRiskScore(), testInstance3.getRiskScore());
		assertEquals(dto.getCreated(), new Long(testInstance3.getCreated().getTime()));
		assertEquals(dto.getUpdated(), null);
	}
	
	@Test
	public void toInvestmentList() {
		List<Investment> testList = new ArrayList<>();
		testList.add(testInstance1);
		testList.add(testInstance2);
		testList.add(testInstance3);
		
		InvestmentListDTO dto = investmentAssembler.toListDTO(testList);
		assertNotNull(dto);
		List<InvestmentListDetailsDTO> dtos = dto.getList();
		assertNotNull(dtos);
		assertEquals(dtos.size(), testList.size());
		for (int i=0; i<dtos.size(); i++) {
			InvestmentListDetailsDTO item = dtos.get(i);
			Investment investment = testList.get(i);
			assertNotNull(item);
			assertEquals(item.getId(), investment.getId());
		}
	}
}
