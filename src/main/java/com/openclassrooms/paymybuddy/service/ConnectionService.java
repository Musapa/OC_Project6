package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;

public interface ConnectionService {

	List<User> findUnconnectedUsers(Long id);
	
	List<User> findConnectedUsers(Long id);
	
	void saveConnection(Connection connection);

}
