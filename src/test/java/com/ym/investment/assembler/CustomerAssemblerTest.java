package com.ym.investment.assembler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ym.investment.domain.Customer;
import com.ym.investment.domain.Investment;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.dto.CustomerListDTO;
import com.ym.investment.dto.CustomerListDetailsDTO;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.service.InvestmentService;
import com.ym.investment.service.PortfolioService;


public class CustomerAssemblerTest {
	private Customer testInstance1;
	private Customer testInstance2;
	private Customer testInstance3;

	List<Portfolio> pList = new ArrayList<>();
	List<Investment> iList = new ArrayList<>();

	@Before
	public void init() {
		testInstance1 = makeObj(true);
		testInstance2 = makeObj(true);
		testInstance3 = makeObj(false);

		PortfolioService service = mock(PortfolioService.class);
		CustomerAssembler ca = new CustomerAssembler();
		ca.setPortfolioService(service);

		Portfolio p1 = makePortfolio();
		Portfolio p2 = makePortfolio();
		pList.add(p1);
		pList.add(p2);
		when(service.list(anyMap())).thenReturn(pList);

		Investment i1 = makeInvestment();
		Investment i2 = makeInvestment();
		Investment i3 = makeInvestment();
		iList.add(i1);
		iList.add(i2);
		iList.add(i3);

		InvestmentService iService = mock(InvestmentService.class);
		PortfolioAssembler pa = new PortfolioAssembler();
		pa.setInvestmentService(iService);
		when(iService.recommend(anyInt(),  anyInt())).thenReturn(iList);
	}

	private Portfolio makePortfolio() {
		Portfolio obj = new Portfolio();
		long id = (long)(Math.random() * 1000);
		obj.setId(id);
		obj.setName("Test Portfolio " + id);
		obj.setReturnPreference((int)(Math.random() * 10));
		obj.setRiskTolerance((int)(Math.random() * 10));
		obj.setCreated(new Date(System.currentTimeMillis()));
		obj.setOwner(testInstance1);

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

	private Customer makeObj(boolean setUpdateDate) {
		Customer obj = new Customer();
		long id = (long)(Math.random() * 1000);
		obj.setId(id);
		obj.setName("Test Customer " + id);
		obj.setMobile("mobile " + id);
		obj.setEmail("email" + id + "@email.com");
		obj.setSecuritiesNum("securities " + id);
		obj.setCreated(new Date(System.currentTimeMillis()));
		if (setUpdateDate) {
			obj.setUpdated(new Date(System.currentTimeMillis()));
		}
		return obj;
	}

	@Test
	public void toCustomerDetails() {
		CustomerDetailsDTO dto = CustomerAssembler.toCustomerDetailsDTO(testInstance1);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance1.getId());
		assertEquals(dto.getName(), testInstance1.getName());
		assertEquals(dto.getEmail(), testInstance1.getEmail());
		assertEquals(dto.getMobile(), testInstance1.getMobile());
		assertEquals(dto.getSecuritiesNum(), testInstance1.getSecuritiesNum());
		assertNull(dto.getPortfolios());
	}

	@Test
	public void toCustomerListDetailsWithUpdateDate() {
		CustomerListDetailsDTO dto = CustomerAssembler.toCustomerListDetailsDTO(testInstance1);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance1.getId());
		assertEquals(dto.getName(), testInstance1.getName());
		assertEquals(dto.getEmail(), testInstance1.getEmail());
		assertEquals(dto.getMobile(), testInstance1.getMobile());
		assertEquals(dto.getSecuritiesNum(), testInstance1.getSecuritiesNum());
		assertEquals(dto.getCreated(), new Long(testInstance1.getCreated().getTime()));
		assertEquals(dto.getUpdated(), new Long(testInstance1.getUpdated().getTime()));
	}
	
	@Test
	public void toCustomerListDetailsWithoutUpdateDate() {
		CustomerListDetailsDTO dto = CustomerAssembler.toCustomerListDetailsDTO(testInstance3);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), testInstance3.getId());
		assertEquals(dto.getName(), testInstance3.getName());
		assertEquals(dto.getEmail(), testInstance3.getEmail());
		assertEquals(dto.getMobile(), testInstance3.getMobile());
		assertEquals(dto.getSecuritiesNum(), testInstance3.getSecuritiesNum());
		assertEquals(dto.getCreated(), new Long(testInstance3.getCreated().getTime()));
		assertEquals(dto.getUpdated(), null);
	}
	
	@Test
	public void toCustomerList() {
		List<Customer> testList = new ArrayList<>();
		testList.add(testInstance1);
		testList.add(testInstance2);
		testList.add(testInstance3);
		
		CustomerListDTO dto = CustomerAssembler.toCustomerListDTO(testList);
		assertNotNull(dto);
		List<CustomerListDetailsDTO> dtos = dto.getList();
		assertNotNull(dtos);
		assertEquals(dtos.size(), testList.size());
		for (int i=0; i<dtos.size(); i++) {
			CustomerListDetailsDTO item = dtos.get(i);
			Customer investment = testList.get(i);
			assertNotNull(item);
			assertEquals(item.getId(), investment.getId());
		}
	}
	
	@Test
	public void insertPortfolioWithoutRecommendations() {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();
		long id = (long)(Math.random() * 1000);
		dto.setId(id);

		CustomerAssembler.insertPortfolio(dto, false);
		List<PortfolioDetailsDTO> portfolios = dto.getPortfolios();
		assertNotNull(portfolios);
		assertEquals(portfolios.size(), pList.size());
		
		for (int i=0; i<pList.size(); i++) {
			Portfolio source = pList.get(i);
			PortfolioDetailsDTO target = portfolios.get(i);
			
			assertNotNull(target);
			assertEquals(source.getId(), target.getId());
			assertNull(target.getRecommendations());
		}
	}


	@Test
	public void insertPortfolioWithRecommendations() {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();
		long id = (long)(Math.random() * 1000);
		dto.setId(id);

		CustomerAssembler.insertPortfolio(dto, true);
		List<PortfolioDetailsDTO> portfolios = dto.getPortfolios();
		assertNotNull(portfolios);
		assertEquals(portfolios.size(), pList.size());

		portfolios.stream().forEach(pDTO -> {
			List<InvestmentDetailsDTO> recommendations = pDTO.getRecommendations();

			assertNotNull(recommendations);
			assertEquals(recommendations.size(), iList.size());

			for (int i=0; i<iList.size(); i++) {
				InvestmentDetailsDTO target = recommendations.get(i);
				Investment source = iList.get(i);

				assertNotNull(target);
				assertEquals(target.getId(), source.getId());
			}
		});
	}
}
