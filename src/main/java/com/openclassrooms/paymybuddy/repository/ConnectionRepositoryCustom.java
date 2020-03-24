package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Connection;

public interface ConnectionRepositoryCustom {

	List<Connection> findConnection(Long id);
}
