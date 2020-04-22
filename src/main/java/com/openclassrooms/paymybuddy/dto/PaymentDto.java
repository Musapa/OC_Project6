package com.openclassrooms.paymybuddy.dto;

import java.math.BigDecimal;

public class PaymentDto {

	private BigDecimal amount;
	private String description;
	private String connection;
	
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	
	
}
