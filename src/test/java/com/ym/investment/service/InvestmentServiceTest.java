package com.ym.investment.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ym.investment.domain.Investment;
import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.exception.NotFoundException;
import com.ym.investment.repository.InvestmentRepository;

import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InvestmentServiceTest {
	
	InvestmentService investmentService = new InvestmentService();
	@Autowired
	InvestmentRepository investmentRepository;
	Map<Long, Investment> testList = new HashMap<>();
	
	@Before
	public void init() {
		investmentService.setRepository(investmentRepository);
		
		int[][] values = {
				{6, 7},
				{6, 6},
				{7, 6},
				{7, 7}
		};
		
		for (int i=0; i<values.length; i++) {
			Investment obj = makeObj(values[i]);
			obj = investmentRepository.save(obj); // note: id is sequence starting from 30, therefore it will be the index of values + 30
			testList.put(obj.getId(), obj);
		}
	}
	
	@Test
	public void listSuccess() {
		List<Investment> list = investmentService.list(null);
		
		assertNotNull(list);
		assertEquals(list.size(), testList.size());
		list.stream().forEach(item -> {
			Investment testObj = testList.get(item.getId());
			assertInvestment(item, testObj);
		});
	}
	
	@Test
	public void getSuccess() {
		Investment test = testList.values().iterator().next();
		Investment obj = investmentService.get(test.getId(), null);
		
		assertNotNull(obj);
		assertEquals(obj.getName(), test.getName());
		assertEquals(obj.getReturnScore(), test.getReturnScore());
		assertEquals(obj.getRiskScore(), test.getRiskScore());
		assertEquals(obj.getCreated(), test.getCreated());
		assertEquals(obj.getUpdated(), test.getUpdated());
	}
	
	@Test
	public void getNonExist() {
		Investment obj = investmentService.get(10001l, null);
		assertNull(obj);
	}

	@Test
	public void createSuccess() {

		InvestmentDetailsDTO source = makeDTO();
		Investment created = investmentService.create(source, null);

		assertNotNull(created);
		assertTrue(created.getId() > 0);

		Investment obj = investmentRepository.findOne(created.getId());
		assertEquals(obj.getName(),  source.getName());
		assertEquals(obj.getReturnScore(), source.getReturnScore());
		assertEquals(obj.getRiskScore(), source.getRiskScore());
		assertNotNull(obj.getCreated());
		assertNull(obj.getUpdated());
	}

	@Test
	public void updateSuccess() {

		Investment test = testList.values().iterator().next();
		InvestmentDetailsDTO dto = new InvestmentDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Investment " + num);
		dto.setReturnScore((int)(Math.random() * 10));
		dto.setRiskScore((int)(Math.random() * 10));

		Investment updated = investmentService.update(test.getId(), dto, null);

		assertNotNull(updated);
		assertEquals(updated.getId(), test.getId());

		assertEquals(updated.getName(), dto.getName());
		assertEquals(updated.getReturnScore(), dto.getReturnScore());
		assertEquals(updated.getRiskScore(), dto.getRiskScore());
		assertEquals(updated.getCreated(), test.getCreated());
		assertNotNull(updated.getUpdated());
	}

	@Test
	public void updateNonExist() {
		InvestmentDetailsDTO dto = new InvestmentDetailsDTO();

		long num = (long)(Math.random() * 1000);
		dto.setName("Investment " + num);

		try {
			investmentService.update(10001l, dto, null);
			assertTrue(false);
		}catch (NotFoundException e) {
			assertTrue(true);
		}catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void deleteSuccess() {
		Investment test = testList.values().iterator().next();

		investmentService.delete(test.getId(), null);

		Investment deleted = investmentRepository.findOne(test.getId());
		assertNull(deleted);
	}
	
	@Test
	public void recommend() {
		@AllArgsConstructor
		class TestVal {
			private int returnScore;
			private int riskScore;
			private int[] matches; // ids of investments that will match
			private boolean[][] matchCombi; // for each match, the 1st boolean is if returnScore match, the 2nd boolean is if riskScore match
		}
		TestVal[] testVals = {
			new TestVal(5, 5, new int[] {}, new boolean[][] {}),
			new TestVal(5, 6, new int[] {31, 32}, new boolean[][]{{false, true}, {false, true}}),
			new TestVal(5, 7, new int[] {30, 33}, new boolean[][]{{false, true}, {false, true}}),
			new TestVal(5, 8, new int[] {}, new boolean[][]{}),

			new TestVal(6, 5, new int[] {30, 31}, new boolean[][]{{true, false}, {true, false}}),
			new TestVal(6, 6, new int[] {30, 31, 32}, new boolean[][]{{true, false}, {true, true}, {false, true}}),
			new TestVal(6, 7, new int[] {30, 31, 33}, new boolean[][]{{true, true}, {true, false}, {false, true}}),
			new TestVal(6, 8, new int[] {30, 31}, new boolean[][]{{true, false}, {true, false}}),
			
			new TestVal(7, 5, new int[] {32, 33}, new boolean[][]{{true, false}, {true, false}}),
			new TestVal(7, 6, new int[] {31, 32, 33}, new boolean[][]{{false, true}, {true, true}, {true, false}}),
			new TestVal(7, 7, new int[] {30, 32, 33}, new boolean[][]{{false, true}, {true, false}, {true, true}}),
			new TestVal(7, 8, new int[] {32, 33}, new boolean[][]{{true, false}, {true, false}}),

			new TestVal(8, 5, new int[] {}, new boolean[][]{}),
			new TestVal(8, 6, new int[] {31, 32}, new boolean[][]{{false, true}, {false, true}}),
			new TestVal(8, 7, new int[] {30, 33}, new boolean[][]{{false, true}, {false, true}}),
			new TestVal(8, 8, new int[] {}, new boolean[][]{}),
		};
		
		for (int i=0; i<testVals.length; i++) {
			TestVal testVal = testVals[i];
			List<Investment> recommends = investmentService.recommend(testVal.riskScore, testVal.returnScore);
			
			assertNotNull(recommends);
			assertEquals(recommends.size(), testVal.matches.length);
			
			recommends.sort(new Comparator<Investment>() { // sort recommends by id

				@Override
				public int compare(Investment o1, Investment o2) {
					return new Long(o1.getId()).compareTo(o2.getId());
				}
			});
			
			for (int x=0; x<testVal.matches.length; x ++) {
				int id = testVal.matches[x];
				boolean[] match = testVal.matchCombi[x];
				Investment investment = recommends.get(x);
				
				assertEquals(investment.getId(), id);
				if (match[0]) {
					assertEquals(investment.getReturnScore(), testVal.returnScore);
				}else {
					assertNotEquals(investment.getReturnScore(), testVal.returnScore);
				}
				if (match[1]) {
					assertEquals(investment.getRiskScore(), testVal.riskScore);
				}else {
					assertNotEquals(investment.getRiskScore(), testVal.riskScore);
				}
			}
		}
	}

	private InvestmentDetailsDTO makeDTO() {
		InvestmentDetailsDTO dto = new InvestmentDetailsDTO();
		long num = (long)(Math.random() * 1000);
		dto.setName("Investment " + num);
		dto.setReturnScore((int)(Math.random() * 10));
		dto.setRiskScore((int)(Math.random() * 10));

		return dto;
	}
	
	private Investment makeObj(int[] values) {
		Investment obj = new Investment();
		long num = (long)(Math.random() * 1000);
		obj.setName("Investment " + num);
		obj.setReturnScore(values[0]);
		obj.setRiskScore(values[1]);
		obj.setCreated(new Date(System.currentTimeMillis()));
		obj.setUpdated(new Date(System.currentTimeMillis()));
		
		return obj;
	}
	
	private void assertInvestment(Investment source, Investment target) {
		assertNotNull(target);
		assertEquals(target.getName(), source.getName());
		assertEquals(target.getReturnScore(), source.getReturnScore());
		assertEquals(target.getRiskScore(), source.getRiskScore());
		assertEquals(target.getCreated(), source.getCreated());
		assertEquals(target.getUpdated(), source.getUpdated());
	}
}
