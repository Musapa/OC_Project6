package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;

public interface TransactionService {

	void save(Transaction transaction);

	List<Transaction> findTransactions(User recipient);

}
