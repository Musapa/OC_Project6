package com.openclassrooms.paymybuddy.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.domain.User;
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
		List<User> users = connectionService.findConnectedUsers(currentUser.getId());
	
		model.addObject("users", users);
		model.setViewName("transaction/transaction");
		return model;
	}
}
