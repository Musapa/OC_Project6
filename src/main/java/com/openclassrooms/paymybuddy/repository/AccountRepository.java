package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.domain.Account;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, Long>{

}

