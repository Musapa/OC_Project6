package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;

public interface ConnectionService {

	List<User> findUnconnectedUsers(User user);
	
	List<User> findConnectedUsers(User user);
	
	void saveConnection(Connection connection);

}
