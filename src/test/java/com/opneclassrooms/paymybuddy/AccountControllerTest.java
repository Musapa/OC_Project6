package com.opneclassrooms.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.paymybuddy.Application;
import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@WithMockUser(username = "test@mail.com", roles = { "ADMIN" })
public class AccountControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void accountHomeTest() throws Exception {
		mockMvc.perform(post("/signup").param("email", "test@mail.com").param("password", "1234"))
		.andExpect(view().name("user/signup")).andExpect(model().errorCount(0))
		.andExpect(status().isOk());
		
		MvcResult result = mockMvc.perform(get("/home/account")).andExpect(view().name("account/account"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
		
		User user = new User();
		user.setId(1L);
		user.setEmail("test@mail.com");
		user.setPassword("1234");
		
		assertEquals(1L, user.getId(), 0);
		assertEquals("test@mail.com", user.getEmail());
		assertEquals("1234", user.getPassword());
		
		Account account = new Account();
		account.setId(2L);
		account.setBalance(new BigDecimal("100"));
		
		assertEquals(2L, account.getId(), 0);
		assertEquals("100", account.getBalance());
		

	}
}
