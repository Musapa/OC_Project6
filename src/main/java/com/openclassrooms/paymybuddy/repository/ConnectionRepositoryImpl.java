package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;

@Repository	
public class ConnectionRepositoryImpl implements ConnectionRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
    @Override
    public List<User> findUnconnectedUsers(User user) {
        Query query = entityManager.createNativeQuery("SELECT * FROM user WHERE id NOT IN (SELECT user_id FROM connection WHERE account_id =?) AND id != ?", User.class);
        query.setParameter(1, user.getAccount().getId());
        query.setParameter(2, user.getId());
        return query.getResultList();
    }
    
    @Override
    public List<Connection> findConnections(User user){
        Query query = entityManager.createNativeQuery("SELECT * FROM connection WHERE account_id = ?", Connection.class);
        query.setParameter(1, user.getAccount().getId());
        return query.getResultList();
    }
}