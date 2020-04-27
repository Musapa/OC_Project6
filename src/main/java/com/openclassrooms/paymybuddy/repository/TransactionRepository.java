package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findTransactions(User recipient);

}
