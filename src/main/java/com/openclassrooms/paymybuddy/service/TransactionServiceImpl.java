package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public void save(Transaction transaction) {
		transactionRepository.save(transaction);
	}
	
	@Override
	public List<Transaction> findTransactions(User recipient){
		return transactionRepository.findTransactions(recipient);
	}
}

