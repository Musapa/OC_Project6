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
public class ConnectionControllerTest extends BaseDataTest {

	@Test
	public void connectionHomeTest() throws Exception {
		super.testRegisterUser();
		super.testUserRepository();
		super.testAddConnection();
		super.testConnectionRepository();
	}
}
