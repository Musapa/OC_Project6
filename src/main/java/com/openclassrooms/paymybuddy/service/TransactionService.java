package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.PaymentDto;

public interface TransactionService {

	void save(Transaction transaction);

	List<Transaction> findTransactions(Account account);
	
	void pay(User thisUser, PaymentDto payment);

}
