package com.openclassrooms.paymybuddy.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PaymentDto {
		
	@NotNull
	@DecimalMin(value="0.9", message = "Amount must not be zero")
	private BigDecimal amount;
	@NotEmpty(message = "Description must not be blank.")
	private String description;
	@NotEmpty(message = "Connection must not be blank.")
	private String connection;
		
	public PaymentDto() {
		this.amount = BigDecimal.ZERO;
		this.description = "";
		this.connection = "";
	}
	
	
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
