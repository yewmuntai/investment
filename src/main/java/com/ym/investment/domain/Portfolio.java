package com.ym.investment.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Portfolio {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@ManyToOne(optional=false)
	private Customer owner;
	
	private int riskTolerance;
	private int returnPreference;

	private Date created;
	private Date updated;

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}
}