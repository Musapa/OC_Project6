package com.opneclassrooms.paymybuddy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.paymybuddy.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	// LoginInvalidTest
	/*
	 * @Test public void loginInvalid() throws Exception { MvcResult result =
	 * mockMvc .perform(get("/login").param("email",
	 * "test@mail.com").param("password", "1234"))
	 * .andExpect(status().isNotFound()).andReturn();
	 * 
	 * String json = result.getResponse().getContentAsString();
	 * 
	 * User findUser = objectMapper.readValue(json, new TypeReference<User>() {});
	 * 
	 * assertEquals("There is no user with this email", 0, findUser.getEmail()); }
	 */

	// ValidRegisterTest
	@Test
	public void testRegistration() throws Exception {

		mockMvc.perform(post("/signup").param("email", "test@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(0)).andExpect(status().isOk());

		mockMvc.perform(post("/signup").param("email", "test@mail.com").param("password", "1234"))
				.andExpect(view().name("user/signup")).andExpect(model().errorCount(1)).andExpect(status().isOk());

		mockMvc.perform(post("/login").param("email", "test@mail.com").param("password", "1234"))
				.andExpect(status().isOk());
		
		
	}

}
