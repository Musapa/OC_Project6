package com.openclassrooms.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
}
