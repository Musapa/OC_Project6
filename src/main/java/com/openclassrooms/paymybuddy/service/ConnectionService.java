package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Connection;

public interface ConnectionService {

	List<Connection> findConnection(Long id);

	void saveConnection(Connection connection);

}
