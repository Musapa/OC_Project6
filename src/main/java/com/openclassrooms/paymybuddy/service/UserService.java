package com.openclassrooms.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getByUsertId(Long userId) {
		return userRepository.findById(userId).get();
	}

	public User addUser(User user) {
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());

		return userRepository.save(newUser);
	}

	public void findUserbyEmail(String email) {
	}
}
