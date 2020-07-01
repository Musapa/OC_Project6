package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Connection;

@Repository("connectionRepository")
public interface ConnectionRepository extends JpaRepository<Connection, Long>, ConnectionRepositoryQuery{

}
