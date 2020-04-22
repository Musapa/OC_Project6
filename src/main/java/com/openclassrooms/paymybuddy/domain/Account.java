package com.openclassrooms.paymybuddy.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private Long id;

	@Column(name = "balance", nullable = false)
	private BigDecimal balance;

	@OneToOne(mappedBy = "account")
	private User user;

	@OneToMany(mappedBy = "account")
	private Set<Transaction> transactions;
	
	@OneToMany(mappedBy = "account")
	private Set<Connection> connections;

	public Account() {
		balance = new BigDecimal(0);
		transactions = new HashSet<>();
		connections = new HashSet<>();
	}
	
	public Account(User user) {
		this.user = user;
		balance = new BigDecimal(100.00);
		transactions = new HashSet<>();
		connections = new HashSet<>();
	}

	public void withdraw() {

	}

	public void deposit() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<Connection> getConnections() {
		return connections;
	}

	public void setConnections(Set<Connection> connections) {
		this.connections = connections;
	}

}
