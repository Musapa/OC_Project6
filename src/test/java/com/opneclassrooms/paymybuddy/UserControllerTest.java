package com.opneclassrooms.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.paymybuddy.Application;
import com.openclassrooms.paymybuddy.domain.User;

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

	//LoginInvalidTest
	@Test
	public void loginInvalid() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/login").param("email", "test@mail.com").param("password", "1234"))
				.andExpect(status().isNotFound()).andReturn();
		
		String json = result.getResponse().getContentAsString();
		
		List<User> findUser = objectMapper.readValue(json, new TypeReference<List<User>>() {});
		
		assertEquals("There is no user with this email", 0, findUser.size());
	}
	
	//ValidRegisterTest
	@Test
	public void registerUserValid() throws Exception {
		User users = new User();
		
		String jsonContent = objectMapper.writeValueAsString(users);

		MvcResult result = mockMvc.perform(post("/signup").content(jsonContent).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		String json = result.getResponse().getContentAsString();
		User userResult = objectMapper.readValue(json, User.class);

		assertEquals("Email correctly returned", true, users.getEmail().equals(userResult.getEmail()));
		assertEquals("Password correctly returned", true, users.getPassword().equals(userResult.getPassword()));
		
	}
}
