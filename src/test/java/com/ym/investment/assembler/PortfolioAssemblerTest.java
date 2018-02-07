package com.ym.investment.assembler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ym.investment.domain.Portfolio;
import com.ym.investment.domain.Investment;
import com.ym.investment.domain.Customer;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.dto.PortfolioListDTO;
import com.ym.investment.dto.PortfolioListDetailsDTO;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.service.InvestmentService;

import static org.mockito.Mockito.*;

public class PortfolioAssemblerTest {
	private Portfolio testInstance1;
	private Portfolio testInstance2;
	private Portfolio testInstance3;
	private Customer customer;

	List<Portfolio> pList = new ArrayList<>();
	List<Investment> iList = new ArrayList<>();

	@Before
	public void init() {
		customer = makeCustomer();
		
		testInstance1 = makeObj(true);
		testInstance2 = makeObj(true);
		testInstance3 = makeObj(false);

		InvestmentService iService = mock(InvestmentService.class);
		PortfolioAssembler pa = new PortfolioAssembler();
		pa.setInvestmentService(iService);

		Investment i1 = makeInvestment();
		Investment i2 = makeInvestment();
		Investment i3 = makeInvestment();
		iList.add(i1);
		iList.add(i2);
		iList.add(i3);

		when(iService.recommend(anyInt(),  anyInt())).thenReturn(iList);
	}

	private Portfolio makeObj(boolean setUpdateDate) {
		Portfolio obj = new Portfolio();
		long id = (long)(Math.random() * 1000);
		obj.setId(id);
		obj.setName("Test Portfolio " + id);
		obj.setReturnPreference((int)(Math.random() * 10));
		obj.setRiskTolerance((int)(Math.random() * 10));
		obj.setCreated(new Date(System.currentTimeMillis()));
		obj.setOwner(customer);
		if (setUpdateDate) {
			obj.setUpdated(new Date(System.currentTimeMillis()));
		}

		return obj;
	}
	
	private Investment makeInvestment() {
		Investment obj = new Investment();
		long id = (long)(Math.random() * 1000);
		obj.setId(id);
		obj.setName("Test Investment " + id);
		obj.setReturnScore((int)(Math.random() * 10));
		obj.setRiskScore((int)(Math.random() * 10));
		obj.setCreated(new Date(System.currentTimeMillis()));
		return obj;
	}

	private Customer makeCustomer() {
		Customer obj = new Customer();
		obj.setName("Test Customer");
		obj.setMobile("11111");
		obj.setEmail("customer@email.com");
		obj.setSecuritiesNum("11111");
		return obj;
	}

	@Test
	public void toPortfolioDetails() {
		PortfolioDetailsDTO dto = PortfolioAssembler.toPortfolioDetailsDTO(testInstance1);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance1.getId());
		assertEquals(dto.getName(), testInstance1.getName());
		assertEquals(dto.getReturnPreference(), testInstance1.getReturnPreference());
		assertEquals(dto.getRiskTolerance(), testInstance1.getRiskTolerance());
		assertEquals(dto.getOwner(), testInstance1.getOwner().getId());
		assertNull(dto.getRecommendations());
	}

	@Test
	public void toPortfolioListDetailsWithUpdateDate() {
		PortfolioListDetailsDTO dto = PortfolioAssembler.toPortfolioListDetailsDTO(testInstance1);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance1.getId());
		assertEquals(dto.getName(), testInstance1.getName());
		assertEquals(dto.getReturnPreference(), testInstance1.getReturnPreference());
		assertEquals(dto.getRiskTolerance(), testInstance1.getRiskTolerance());
		assertEquals(dto.getOwner(), testInstance1.getOwner().getName());
		assertEquals(dto.getCreated(), new Long(testInstance1.getCreated().getTime()));
		assertEquals(dto.getUpdated(), new Long(testInstance1.getUpdated().getTime()));
	}
	
	@Test
	public void toPortfolioListDetailsWithoutUpdateDate() {
		PortfolioListDetailsDTO dto = PortfolioAssembler.toPortfolioListDetailsDTO(testInstance3);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance3.getId());
		assertEquals(dto.getName(), testInstance3.getName());
		assertEquals(dto.getReturnPreference(), testInstance3.getReturnPreference());
		assertEquals(dto.getRiskTolerance(), testInstance3.getRiskTolerance());
		assertEquals(dto.getOwner(), testInstance3.getOwner().getName());
		assertEquals(dto.getCreated(), new Long(testInstance3.getCreated().getTime()));
		assertEquals(dto.getUpdated(), null);
	}
	
	@Test
	public void toPortfolioList() {
		List<Portfolio> testList = new ArrayList<>();
		testList.add(testInstance1);
		testList.add(testInstance2);
		testList.add(testInstance3);
		
		PortfolioListDTO dto = PortfolioAssembler.toPortfolioListDTO(testList);
		assertNotNull(dto);
		List<PortfolioListDetailsDTO> dtos = dto.getList();
		assertNotNull(dtos);
		assertEquals(dtos.size(), testList.size());
		for (int i=0; i<dtos.size(); i++) {
			PortfolioListDetailsDTO item = dtos.get(i);
			Portfolio investment = testList.get(i);
			assertNotNull(item);
			assertEquals(item.getId(), investment.getId());
		}
	}
	
	@Test
	public void insertRecommendations() {
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();
		long id = (long)(Math.random() * 1000);
		dto.setId(id);

		PortfolioAssembler.insertRecommendations(dto);
		List<InvestmentDetailsDTO> recommendations = dto.getRecommendations();

		assertNotNull(recommendations);
		assertEquals(recommendations.size(), iList.size());

		for (int i=0; i<iList.size(); i++) {
			InvestmentDetailsDTO target = recommendations.get(i);
			Investment source = iList.get(i);

			assertNotNull(target);
			assertEquals(target.getId(), source.getId());
		}
	}
}
