package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.User;

public interface ConnectionRepositoryCustom {

	List<User> findUnconnectedUsers(Long id);
}
