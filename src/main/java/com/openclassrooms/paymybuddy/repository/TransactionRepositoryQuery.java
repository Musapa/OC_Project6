package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Transaction;

public interface TransactionRepositoryQuery {
	public List<Transaction> findTransactions(Account account);
}
