package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Transaction;

public interface TransactionService {

	void save(Transaction transaction);

	List<Transaction> findTransactions(Account account);

}
