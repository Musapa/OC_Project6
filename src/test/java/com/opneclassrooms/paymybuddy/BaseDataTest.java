package com.opneclassrooms.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.UserSelectDto;
import com.openclassrooms.paymybuddy.repository.AccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;

public class BaseDataTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	protected void testRegisterUser() throws Exception {
		mockMvc.perform(post("/signup").param("email", "test@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(0)).andExpect(status().isOk());

		mockMvc.perform(post("/signup").param("email", "test@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(1)).andExpect(status().isOk());

		mockMvc.perform(post("/signup").param("email", "test2@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(0)).andExpect(status().isOk());

		mockMvc.perform(post("/signup").param("email", "test2@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(1)).andExpect(status().isOk());

		mockMvc.perform(post("/signup").param("email", "test3@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(0)).andExpect(status().isOk());

		mockMvc.perform(post("/signup").param("email", "test3@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(1)).andExpect(status().isOk());

		MvcResult result = mockMvc.perform(get("/home/connection")).andExpect(view().name("connection/connection"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	protected void testAddConnection() throws Exception {
		ConnectionDto connectionDto = new ConnectionDto();
		User user1 = userRepository.findByEmail("test2@mail.com");
		connectionDto.addUser(new UserSelectDto(user1, true));
		mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/home/connection", connectionDto))
				.andExpect(view().name("redirect:/home/transaction/")).andExpect(model().hasNoErrors());
	}

	protected void testUserRepository() {
		assertEquals("3 users expected", 3, userRepository.count());
		assertNotNull("Can't find email: test@mail.com", userRepository.findByEmail("test@mail.com"));
		assertNotNull("Can't find email: test2@mail.com", userRepository.findByEmail("test2@mail.com"));
		assertNotNull("Can't find email: test3@mail.com", userRepository.findByEmail("test3@mail.com"));
	}

	protected void testConnectionrRepository() {

	}

	protected void testAccountRepository() {
		User user = userRepository.findByEmail("test2@mail.com");
		Account account = user.getAccount();
		assertEquals("Initial balance should be 100", account.getBalance(), BigDecimal.valueOf(10000, 2));
	}

	protected void testAccountBalance() throws Exception {
		MvcResult result = mockMvc.perform(get("/home/account")).andExpect(view().name("account/account"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		int foundUser = content.indexOf("test@mail.com");
		assertNotEquals("Cannot find user", foundUser, -1);
		int foundBalance = content
				.indexOf("<p class=\"pstyle_account\"> Balance: <span style=\"font-weight: 700;\">100.00</span></p>");
		assertNotEquals("Cannot find balance", foundBalance, -1);
	}
}
