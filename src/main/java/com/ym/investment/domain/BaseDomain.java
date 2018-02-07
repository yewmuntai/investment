package com.ym.investment.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseDomain {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Date created;
	private Date updated;
}
