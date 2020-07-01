package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Transaction;

public class TransactionRepositoryImpl implements TransactionRepositoryQuery {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Transaction> findTransactions(Account account) {
		Query query = entityManager.createNativeQuery("SELECT * FROM transaction WHERE account_id =?",
				Transaction.class);
		query.setParameter(1, account.getId());
		return query.getResultList();
	}
}
