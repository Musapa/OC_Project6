package com.openclassrooms.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.repository.AccountRepository;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;
	
}
