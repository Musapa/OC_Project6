package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.User;

@Repository	
public class ConnectionRepositoryImpl implements ConnectionRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
    @Override
    public List<User> findUnconnectedUsers(Long id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USER WHERE id NOT IN (SELECT USER_ID FROM CONNECTION WHERE OWNER_ID = ?) AND id != ?", User.class);
        query.setParameter(1, id);
        query.setParameter(2, id);
        return query.getResultList();
    }
    
    @Override
	public List<User> findConnectedUsers(Long id){
        Query query = entityManager.createNativeQuery("SELECT * FROM USER WHERE id IN (SELECT USER_ID FROM CONNECTION WHERE OWNER_ID = ?)", User.class);
        query.setParameter(1, id);
        return query.getResultList();
    }
}