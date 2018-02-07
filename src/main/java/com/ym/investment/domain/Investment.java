package com.ym.investment.domain;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Investment extends BaseDomain {
	private String name;
	private int riskScore;
	private int returnScore;
}
