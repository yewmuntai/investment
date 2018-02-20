package com.ym.investment.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ym.investment.domain.Customer;
import com.ym.investment.dto.CustomerDetailsDTO;
import com.ym.investment.exception.NotFoundException;
import com.ym.investment.repository.CustomerRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceTest {
	
	CustomerService customerService = new CustomerService();
	@Autowired
	CustomerRepository customerRepository;
	Map<Long, Customer> testList = new HashMap<>();
	
	@Before
	public void init() {
		customerService.setRepository(customerRepository);
		for (int i=0; i<3; i++) {
			Customer obj = makeObj();
			obj.setCreated(new Date());
			obj = customerRepository.save(obj);
			testList.put(obj.getId(), obj);
		}
	}
	
	@Test
	public void listSuccess() {
		List<Customer> list = customerService.list(null);
		
		assertNotNull(list);
		assertEquals(list.size(), testList.size());
		list.stream().forEach(item -> {
			Customer testObj = testList.get(item.getId());
			assertCustomer(item, testObj);
		});
	}
	
	@Test
	public void getSuccess() {
		Customer test = testList.values().iterator().next();
		Customer obj = customerService.get(test.getId(), null);
		
		assertNotNull(obj);
		assertEquals(obj.getName(), test.getName());
		assertEquals(obj.getEmail(), test.getEmail());
		assertEquals(obj.getMobile(), test.getMobile());
		assertEquals(obj.getSecuritiesNum(), test.getSecuritiesNum());
		assertEquals(obj.getCreated(), test.getCreated());
		assertEquals(obj.getUpdated(), test.getUpdated());
	}
	
	@Test
	public void getNonExist() {
		Customer obj = customerService.get(10001l, null);
		assertNull(obj);
	}

	@Test
	public void createSuccess() {

		CustomerDetailsDTO source = makeDTO();
		Customer created = customerService.create(source, null);

		assertNotNull(created);
		assertTrue(created.getId() > 0);

		Customer obj = customerRepository.findOne(created.getId());
		assertEquals(obj.getName(),  source.getName());
		assertEquals(obj.getEmail(), source.getEmail());
		assertEquals(obj.getMobile(), source.getMobile());
		assertEquals(obj.getSecuritiesNum(), source.getSecuritiesNum());
		assertNotNull(obj.getCreated());
		assertNull(obj.getUpdated());
	}

	@Test
	public void updateSuccess() {

		Customer test = testList.values().iterator().next();
		CustomerDetailsDTO dto = new CustomerDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Customer " + num);
		dto.setMobile("Mobile " + num);
		dto.setEmail("email"+num+"@email.com");
		dto.setSecuritiesNum("securities " + num);
		Customer updated = customerService.update(test.getId(), dto, null);
		assertNotNull(updated);
		assertEquals(updated.getId(), test.getId());

		assertEquals(updated.getName(), dto.getName());
		assertEquals(updated.getEmail(), dto.getEmail());
		assertEquals(updated.getMobile(), dto.getMobile());
		assertEquals(updated.getSecuritiesNum(), dto.getSecuritiesNum());
		assertEquals(updated.getCreated(), test.getCreated());
		assertNotNull(updated.getUpdated());
	}

	@Test
	public void updateNonExist() {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Customer " + num);

		try {
			customerService.update(10001l, dto, null);
			assertTrue(false);
		}catch (NotFoundException e) {
			assertTrue(true);
		}catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void deleteSuccess() {
		Customer test = testList.values().iterator().next();

		customerService.delete(test.getId(), null);

		Customer deleted = customerRepository.findOne(test.getId());
		assertNull(deleted);
	}

	private CustomerDetailsDTO makeDTO() {
		CustomerDetailsDTO dto = new CustomerDetailsDTO();
		long id = (long)(Math.random() * 1000);
		dto.setName("Customer " + id);
		dto.setMobile("Mobile " + id);
		dto.setEmail("email"+id+"@email.com");
		dto.setSecuritiesNum("securities " + id);

		return dto;
	}
	
	private Customer makeObj() {
		Customer obj = new Customer();
		long id = (long)(Math.random() * 1000);
		obj.setId(id);
		obj.setName("Customer " + id);
		obj.setMobile("Mobile " + id);
		obj.setEmail("email"+id+"@email.com");
		obj.setSecuritiesNum("securities " + id);
		
		return obj;
	}
	
	private void assertCustomer(Customer source, Customer target) {
		assertNotNull(target);
		assertEquals(target.getName(), source.getName());
		assertEquals(target.getEmail(), source.getEmail());
		assertEquals(target.getMobile(), source.getMobile());
		assertEquals(target.getSecuritiesNum(), source.getSecuritiesNum());
		assertEquals(target.getCreated(), source.getCreated());
		assertEquals(target.getUpdated(), source.getUpdated());
	}
}
