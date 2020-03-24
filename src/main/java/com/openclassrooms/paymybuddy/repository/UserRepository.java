package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	//method for email is not equals current User
	User findByEmail(String email);
}
