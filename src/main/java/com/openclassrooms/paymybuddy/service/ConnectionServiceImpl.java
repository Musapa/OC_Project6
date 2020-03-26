package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;

@Service("connectionService")
public class ConnectionServiceImpl implements ConnectionService {
	
	@Autowired
	private ConnectionRepository connectionRepository;

	
	@Override
	public List<User> findUnconnectedUsers(Long id){
		return connectionRepository.findUnconnectedUsers(id);
	}
	
	@Override
    public void saveConnection(Connection connection) {
		connectionRepository.save(connection);
    }
	

	
}
