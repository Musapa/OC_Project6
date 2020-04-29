package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;

public interface ConnectionRepositoryCustom {

	List<User> findUnconnectedUsers(User user);
	
	List<Connection> findConnections(User user);
	
}
