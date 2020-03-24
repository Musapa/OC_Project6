package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Connection;

@Repository	
public class ConnectionRepositoryImpl implements ConnectionRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
    @Override
    public List<Connection> findConnection(Long id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM connection WHERE connection_id = ?", Connection.class);
        query.setParameter(1, id);
        return query.getResultList();
    }
}