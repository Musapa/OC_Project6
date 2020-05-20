package com.opneclassrooms.paymybuddy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.openclassrooms.paymybuddy.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@WithMockUser(username = "test@mail.com", roles = { "ADMIN" })
public class TransactionControllerTest extends BaseDataTest {

	@Test
	public void transactionHomeTest() throws Exception {
		super.testRegisterUser();
		super.testAddConnection();
		super.testUserRepository();
		super.testConnectionrRepository();
		
		//TODO mockMvc addTransaction with errorHandling 
		
		// 1 transaction that check fail "110" and than succed and check HTMl
		
		
		// TODO check TransactionRepository and AccountRepository check balance
		
	}

}

