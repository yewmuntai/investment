package com.ym.investment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ym.investment.domain.Customer;
import com.ym.investment.domain.Portfolio;
import com.ym.investment.dto.PortfolioDetailsDTO;
import com.ym.investment.exception.InvestmentException;
import com.ym.investment.exception.NotFoundException;
import com.ym.investment.repository.CustomerRepository;
import com.ym.investment.repository.PortfolioRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PortfolioServiceTest {
	
	PortfolioService portfolioService = new PortfolioService();
	@Autowired
	PortfolioRepository portfolioRepository;
	@Autowired
	CustomerRepository customerRepository;
	Map<Long, Portfolio> testList = new HashMap<>();
	Customer customer;
	
	@Before
	public void init() {
		portfolioService.setRepository(portfolioRepository);
		
		CustomerService customerService = mock(CustomerService.class);
		portfolioService.setCustomerService(customerService);

		customer = new Customer();
		customer.setName("Test Customer");
		customer.setMobile("11111");
		customer.setEmail("customer@email.com");
		customer.setSecuritiesNum("11111");
		customer = customerRepository.save(customer);

		when(customerService.get(10001l, null)).thenReturn(null);	
		when(customerService.get(customer.getId(), null)).thenReturn(customer);	

		for (int i=0; i<3; i++) {
			Portfolio obj = makeObj();
			obj = portfolioRepository.save(obj);
			testList.put(obj.getId(), obj);
		}
	}
	
	@Test
	public void listSuccess() {
		List<Portfolio> list = portfolioService.list(null);
		
		assertNotNull(list);
		assertEquals(list.size(), testList.size());
		list.stream().forEach(item -> {
			Portfolio testObj = testList.get(item.getId());
			assertPortfolio(item, testObj);
		});
	}
	
	@Test
	public void getSuccess() {
		Portfolio test = testList.values().iterator().next();
		Portfolio obj = portfolioService.get(test.getId(), null);
		
		assertNotNull(obj);
		assertEquals(obj.getName(), test.getName());
		assertEquals(obj.getReturnPreference(), test.getReturnPreference());
		assertEquals(obj.getRiskTolerance(), test.getRiskTolerance());
		assertEquals(obj.getCreated(), test.getCreated());
		assertEquals(obj.getUpdated(), test.getUpdated());
	}
	
	@Test
	public void getNonExist() {
		Portfolio obj = portfolioService.get(10001l, null);
		assertNull(obj);
	}

	@Test
	public void createSuccess() {

		PortfolioDetailsDTO source = makeDTO();
		Portfolio created = portfolioService.create(source, null);

		assertNotNull(created);
		assertTrue(created.getId() > 0);

		Portfolio obj = portfolioRepository.findOne(created.getId());
		assertEquals(obj.getName(),  source.getName());
		assertEquals(obj.getReturnPreference(), source.getReturnPreference());
		assertEquals(obj.getRiskTolerance(), source.getRiskTolerance());
		assertNotNull(obj.getCreated());
		assertNull(obj.getUpdated());
	}

	@Test
	public void createInvalidOwner() {

		PortfolioDetailsDTO source = makeDTO();
		source.setOwner(10001l);
		
		try {
			portfolioService.create(source, null);
			assertTrue(false);
		}catch (InvestmentException e) {
			assertEquals(e.getMessage(), "Invalid owner");
		}catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void updateSuccess() {

		Portfolio test = testList.values().iterator().next();
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Portfolio " + num);
		dto.setReturnPreference((int)(Math.random() * 10));
		dto.setRiskTolerance((int)(Math.random() * 10));
		dto.setOwner(customer.getId());

		Portfolio updated = portfolioService.update(test.getId(), dto, null);

		assertNotNull(updated);
		assertEquals(updated.getId(), test.getId());

		assertEquals(updated.getName(), dto.getName());
		assertEquals(updated.getReturnPreference(), dto.getReturnPreference());
		assertEquals(updated.getRiskTolerance(), dto.getRiskTolerance());
		assertEquals(updated.getCreated(), test.getCreated());
		assertNotNull(updated.getUpdated());
	}

	@Test
	public void updateNonExist() {
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Portfolio " + num);

		try {
			portfolioService.update(10001l, dto, null);
			assertTrue(false);
		}catch (NotFoundException e) {
			assertTrue(true);
		}catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void updateInvalidOwner() {

		Portfolio test = testList.values().iterator().next();
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Portfolio " + num);
		dto.setReturnPreference((int)(Math.random() * 10));
		dto.setRiskTolerance((int)(Math.random() * 10));
		dto.setOwner(10001l);

		try {
			portfolioService.update(test.getId(), dto, null);
			assertTrue(false);
		}catch (InvestmentException e) {
			assertEquals(e.getMessage(), "Invalid owner");
		}catch (Exception e) {
			assertTrue(false);
		}
	}


	@Test
	public void deleteSuccess() {
		Portfolio test = testList.values().iterator().next();

		portfolioService.delete(test.getId(), null);

		Portfolio deleted = portfolioRepository.findOne(test.getId());
		assertNull(deleted);
	}

	private PortfolioDetailsDTO makeDTO() {
		PortfolioDetailsDTO dto = new PortfolioDetailsDTO();
		long id = (long)(Math.random() * 1000);
		dto.setName("Test Portfolio " + id);
		dto.setReturnPreference((int)(Math.random() * 10));
		dto.setRiskTolerance((int)(Math.random() * 10));
		dto.setOwner(customer.getId());

		return dto;
	}
	
	private Portfolio makeObj() {
		Portfolio obj = new Portfolio();
		long id = (long)(Math.random() * 1000);
		obj.setName("Test Portfolio " + id);
		obj.setReturnPreference((int)(Math.random() * 10));
		obj.setRiskTolerance((int)(Math.random() * 10));
		obj.setOwner(customer);
		
		return obj;
	}
	
	private void assertPortfolio(Portfolio source, Portfolio target) {
		assertNotNull(target);
		assertEquals(target.getName(), source.getName());
		assertEquals(target.getReturnPreference(), source.getReturnPreference());
		assertEquals(target.getRiskTolerance(), source.getRiskTolerance());
		assertEquals(target.getCreated(), source.getCreated());
		assertEquals(target.getUpdated(), source.getUpdated());
	}	
}