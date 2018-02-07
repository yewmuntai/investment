package com.ym.investment.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.investment.dto.InvestmentDetailsDTO;
import com.ym.investment.domain.Investment;
import com.ym.investment.repository.InvestmentRepository;

/**
 * CRUD service for investment
 *
 */
@Service
public class InvestmentService extends CRUDService<Investment, InvestmentDetailsDTO>{
	@Autowired
	private InvestmentRepository investmentRepository;
	
	@Override
	InvestmentRepository getRepository() {
		return investmentRepository;
	}

	@Override
	void setDomainValues(Investment investment, InvestmentDetailsDTO source, Map<String, String> params) {
		investment.setName(source.getName());
		investment.setReturnScore(source.getReturnScore());
		investment.setRiskScore(source.getRiskScore());
	}

	@Override
	java.lang.Class<Investment> getT1Class() {
		return Investment.class;
	}
	
	/**
	 * For unit test
	 * @param repo InvestmentRepository
	 */
	void setRepository(InvestmentRepository repo) {
		if (investmentRepository == null) {
			investmentRepository = repo;
		}
	}

	/**
	 * To provide a list of recommended investment based on risk tolerance and return preference.
	 * The risk tolerance will be matched to the risk score of the investment and the return reference will be matched to the return score.
	 * 
	 * @param riskTolerance
	 * @param returnPreference
	 * @return List of Investments that matches.
	 */
	public List<Investment> recommend(int riskTolerance, int returnPreference) {
		return investmentRepository.findByRiskScoreOrReturnScore(riskTolerance, returnPreference);
	}
}
