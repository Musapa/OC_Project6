package com.openclassrooms.paymybuddy.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.PaymentDto;
import com.openclassrooms.paymybuddy.service.ConnectionService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class TransactionController {

	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@Autowired
	private ConnectionService connectionService;

	@RequestMapping(value = { "home/transaction" }, method = RequestMethod.GET)
	public ModelAndView getTransaction() {
		ModelAndView model = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Get Name: " + authentication.getName());

		User currentUser = userService.findUserByEmail(authentication.getName());
		List<Connection> connections = connectionService.findConnectedUsers(currentUser);

		List<Transaction> transactions = transactionService.findTransactions(currentUser.getAccount());

		model.addObject("connections", connections);
		model.addObject("transactions", transactions);
		model.addObject("payment", new PaymentDto());
		model.setViewName("transaction/transaction");
		return model;
	}

	@RequestMapping(value = { "home/transaction/pay" }, method = RequestMethod.POST)
	public ModelAndView pay(@Valid @ModelAttribute("payment") PaymentDto form, BindingResult result) {
		ModelAndView model = new ModelAndView();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User thisUser = userService.findUserByEmail(authentication.getName());

		BigDecimal amount = form.getAmount();
		Account account = thisUser.getAccount();
		BigDecimal balance = account.getBalance();
		BigDecimal remainder = balance.subtract(amount);

		if (remainder.compareTo(BigDecimal.ZERO) < 0) {
			result.rejectValue("amount", "error.form", "Insufficient funds.");
		} 
		if (result.hasErrors()) {
			model.setViewName("transaction/transaction");
		}else {
			BigDecimal feeRate = new BigDecimal("0.5");
			BigDecimal fee = amount.multiply(feeRate).divide(new BigDecimal(100));
		
			Optional<Connection> recipient = connectionService.findById(new Long(form.getConnection()));
			Transaction transaction = new Transaction(amount, fee, form.getDescription(), recipient.get(),
					thisUser.getAccount());

			transactionService.save(transaction);
			account.setBalance(remainder);
			userService.saveUser(thisUser);
			
			User recipientUser = recipient.get().getUser();
			Account recipientAccount = recipientUser.getAccount();
			BigDecimal recipientBalance = account.getBalance();
			BigDecimal newRecipientBalance = recipientBalance.add(amount);
			
			recipientAccount.setBalance(newRecipientBalance);
			userService.saveUser(recipientUser);
			
			form = new PaymentDto();
		}
		
		User currentUser = userService.findUserByEmail(authentication.getName());
		List<Connection> connections = connectionService.findConnectedUsers(currentUser);
		List<Transaction> transactions = transactionService.findTransactions(currentUser.getAccount());

		model.addObject("connections", connections);
		model.addObject("transactions", transactions);
		model.addObject("payment", form);
		model.setViewName("transaction/transaction");

		return model;

	}	
}
