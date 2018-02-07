package com.ym.investment.domain;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Customer extends BaseDomain {
	private String name;
	private String email;
	private String securitiesNum;
	private String mobile;
}
