package com.openclassrooms.paymybuddy.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_id")
	private Long id;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "fee", nullable = false)
	private BigDecimal fee;

	@Column(name = "description", nullable = false)
	private String description;
	
	/*@OneToOne
	@Column(name = "recipient", nullable = false)
	private User recipient;*/

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	public Transaction() {
	}

	public Transaction(Long id, BigDecimal amount, BigDecimal fee, String description, Account account) {
		super();
		this.id = id;
		this.amount = amount;
		this.fee = fee;
		this.description = description;
		this.account = account;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
