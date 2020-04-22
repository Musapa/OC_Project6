package com.openclassrooms.paymybuddy.controller;


import java.util.List;

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
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.PaymentDto;
import com.openclassrooms.paymybuddy.dto.UserSelectDto;
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
		
		//add balnce !< 0 on user nešto

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Get Name: " + authentication.getName());

		User currentUser = userService.findUserByEmail(authentication.getName());
		List<User> users = connectionService.findConnectedUsers(currentUser);
	
		model.addObject("users", users);
		model.addObject("payment", new PaymentDto());
		model.setViewName("transaction/transaction");
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
		
		//get connection id, search for connection where user 
		// search user field
		
		//find connection crate new transaction
	
		
		
		return "redirect:/home/transaction/";
	}
}
