package com.openclassrooms.paymybuddy.dto;

import java.math.BigDecimal;

import com.openclassrooms.paymybuddy.domain.User;

public class PaymentDto extends User {

	private BigDecimal ammount;
	private String description;
	private String connection;
	
	
	
	public BigDecimal getAmmount() {
		return ammount;
	}
	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
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
