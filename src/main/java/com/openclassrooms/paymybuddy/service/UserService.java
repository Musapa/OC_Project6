package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.domain.User;

public interface UserService {

	public User findUserByEmail(String email);
	
	public void saveUser(User user);
}
