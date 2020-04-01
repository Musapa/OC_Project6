package com.openclassrooms.paymybuddy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "connection_id")
	private Long id;

	@OneToOne
	private User user;
	
	@OneToOne
	private User owner;

	public Connection() {
	}
	
	public Connection(User user, User owner) {
		super();
		this.user = user;
		this.owner = owner;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
