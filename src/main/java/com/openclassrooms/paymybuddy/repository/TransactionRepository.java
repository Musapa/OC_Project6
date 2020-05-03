package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Transaction;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryCustom {

}
