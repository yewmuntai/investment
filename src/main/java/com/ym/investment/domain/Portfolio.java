package com.ym.investment.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Portfolio extends BaseDomain{
	private String name;
	
	@ManyToOne(optional=false)
	private Customer owner;
	
	private int riskTolerance;
	private int returnPreference;
}
