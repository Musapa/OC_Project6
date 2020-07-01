package com.openclassrooms.paymybuddy.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Role;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.repository.RoleRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@Transactional
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public void createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		user.setAccount(new Account(user));
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public Optional<User> findById(Long id){
		return userRepository.findById(id);
	}
}
