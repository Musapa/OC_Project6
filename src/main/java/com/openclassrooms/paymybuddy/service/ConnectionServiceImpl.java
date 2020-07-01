package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;

@Service("connectionService")
public class ConnectionServiceImpl implements ConnectionService {
	
	@Autowired
	private ConnectionRepository connectionRepository;

	
	@Override
	@Transactional
	public List<User> findUnconnectedUsers(User user){
		return connectionRepository.findUnconnectedUsers(user);
	}
	@Override
	@Transactional
	public List<Connection> findConnectedUsers(User user){
		return connectionRepository.findConnections(user);
	}
	
	@Override
	@Transactional
    public void saveConnection(Connection connection) {
		connectionRepository.save(connection);
    }
	
	@Override
	@Transactional
	public Optional<Connection> findById(Long id){
		return connectionRepository.findById(id);
	}
	

	
}
