package com.openclassrooms.paymybuddy.service;

import java.util.Optional;

import com.openclassrooms.paymybuddy.domain.User;

public interface UserService {

	public User findUserByEmail(String email);
	
	public void createUser(User user);
	
	public void saveUser(User user);

	Optional<User> findById(Long id);

}
