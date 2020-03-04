package com.openclassrooms.paymybuddy.domain;

import java.math.BigDecimal;

public class Transaction {

	private Long id;
	private BigDecimal amount;
	private BigDecimal fee;
	private String description;
	
	
	public Transaction(Long id, BigDecimal amount, BigDecimal fee, String description) {
		super();
		this.id = id;
		this.amount = amount;
		this.fee = fee;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
