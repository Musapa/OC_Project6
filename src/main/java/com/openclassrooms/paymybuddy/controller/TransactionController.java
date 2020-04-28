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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView getTransaction(BindingResult bindingResult, PaymentDto form) {
		ModelAndView model = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Get Name: " + authentication.getName());

		User currentUser = userService.findUserByEmail(authentication.getName());
		List<User> users = connectionService.findConnectedUsers(currentUser);
		
		List<Transaction> transactions = transactionService.findTransactions(null);
		
		//TODO transaction service find transaction like in user
		//TODO check the amount not exceed amount on account and put error on html if there is
		BigDecimal amount = form.getAmount();
		
		if (amount.compareTo(amount) <= 0) {
			bindingResult.rejectValue("transaction", "error.transaction", "Insufficient funds.");
		}
		if (bindingResult.hasErrors()) {
			model.setViewName("transaction/transaction");
		} else {	
		model.addObject("users", users);
		model.addObject("transaction", transactions);
		model.addObject("payment", new PaymentDto());
		model.setViewName("transaction/transaction");
		}
		return model;	
	}
	
	@RequestMapping(value = {"home/transaction/pay"}, method = RequestMethod.POST)
	public String pay(@Valid @ModelAttribute PaymentDto form, Model model, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("There is a error in addPay.");
			return "redirect:/error";
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User thisUser = userService.findUserByEmail(authentication.getName());
		
		BigDecimal amount = form.getAmount();
		BigDecimal feeRate = new BigDecimal("0.5");
		BigDecimal fee = amount.multiply(feeRate).divide(new BigDecimal(100));
		Optional<Connection> recipient = connectionService.findById(new Long(form.getConnection()));
		Transaction transaction = new Transaction(amount, fee, form.getDescription(), recipient.get(), thisUser.getAccount());
		
		transactionService.save(transaction);
		
		return "redirect:/home/transaction/";
	}
}
