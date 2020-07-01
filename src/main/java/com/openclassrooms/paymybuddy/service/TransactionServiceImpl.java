package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.PaymentDto;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ConnectionService connectionService;

	@Override
	@Transactional
	public void save(Transaction transaction) {
		transactionRepository.save(transaction);
	}

	@Override
	@Transactional
	public List<Transaction> findTransactions(Account account) {
		return transactionRepository.findTransactions(account);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void pay(User thisUser, PaymentDto payment) {

		BigDecimal amount = payment.getAmount();
		Account account = thisUser.getAccount();
		BigDecimal balance = account.getBalance();
		BigDecimal remainder = balance.subtract(amount);

		BigDecimal feeRate = new BigDecimal("0.5");
		BigDecimal fee = amount.multiply(feeRate).divide(new BigDecimal(100));

		Optional<Connection> recipient = connectionService.findById(new Long(payment.getConnection()));
		Transaction transaction = new Transaction(amount, fee, payment.getDescription(), recipient.get(),
				thisUser.getAccount());

		save(transaction);
		account.setBalance(remainder);
		userService.saveUser(thisUser);

		User recipientUser = recipient.get().getUser();
		Account recipientAccount = recipientUser.getAccount();
		BigDecimal recipientBalance = recipientAccount.getBalance();
		BigDecimal newRecipientBalance = recipientBalance.add(amount);

		recipientAccount.setBalance(newRecipientBalance);
		userService.saveUser(recipientUser);

		if (remainder.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("Not enoguh money");
		}
	}

}
