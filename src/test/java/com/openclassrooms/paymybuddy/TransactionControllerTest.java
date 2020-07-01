package com.openclassrooms.paymybuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.paymybuddy.Application;
import com.openclassrooms.paymybuddy.domain.Account;
import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.Transaction;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.PaymentDto;
import com.openclassrooms.paymybuddy.dto.UserSelectDto;
import com.openclassrooms.paymybuddy.repository.AccountRepository;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@WithMockUser(username = "test@mail.com", roles = { "ADMIN" })
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class TransactionControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testTransaction() throws Exception {
		/* Register users */
		testRegisterUser();
		/* Check that registered users are in database */
		testUserRepository();
		testAddConnection();
		testConnectionRepository();
		testPay();
		testTransactionRepository();
		testGetTransactions();
	}

	private void testRegisterUser() throws Exception {
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

	private void testAddConnection() throws Exception {
		ConnectionDto connectionDto = new ConnectionDto();
		User user1 = userRepository.findByEmail("test2@mail.com");
		connectionDto.addUser(new UserSelectDto(user1, true));
		mockMvc.perform(post("/home/connection").contentType(MediaType.APPLICATION_FORM_URLENCODED).flashAttr("form",
				connectionDto)).andExpect(view().name("redirect:/home/transaction/")).andExpect(model().hasNoErrors());
	}

	private void testUserRepository() {
		assertEquals("3 users expected", 3, userRepository.count());
		assertNotNull("Can't find email: test@mail.com", userRepository.findByEmail("test@mail.com"));
		assertNotNull("Can't find email: test2@mail.com", userRepository.findByEmail("test2@mail.com"));
		assertNotNull("Can't find email: test3@mail.com", userRepository.findByEmail("test3@mail.com"));
	}

	private void testConnectionRepository() {
		User user = userRepository.findByEmail("test@mail.com");
		List<User> unconnectedUsers = connectionRepository.findUnconnectedUsers(user);
		List<Connection> findConnections = connectionRepository.findConnections(user);

		assertEquals("Expected 1 unconnected user", 1, unconnectedUsers.size());
		assertEquals("Expected 1 connected user", 1, findConnections.size());
	}

	private void testPay() throws Exception {
		User user = userRepository.findByEmail("test@mail.com");
		List<Connection> findConnections = connectionRepository.findConnections(user);
		PaymentDto paymentDto = new PaymentDto();

		paymentDto.setAmount(new BigDecimal(1200));
		paymentDto.setDescription("Invalid Payment");
		paymentDto.setConnection(findConnections.get(0).getId().toString());

		mockMvc.perform(post("/home/transaction/pay").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("payment", paymentDto)).andExpect(view().name("transaction/transaction"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());

		paymentDto.setAmount(new BigDecimal(25));
		paymentDto.setDescription("Valid Payment");

		mockMvc.perform(post("/home/transaction/pay").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("payment", paymentDto)).andExpect(view().name("transaction/transaction"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk());

		paymentDto.setAmount(new BigDecimal(45));
		paymentDto.setDescription("Valid_2 Payment");

		mockMvc.perform(post("/home/transaction/pay").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("payment", paymentDto)).andExpect(view().name("transaction/transaction"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk());
	}

	private void testTransactionRepository() {
		User user = userRepository.findByEmail("test@mail.com");
		Account account = user.getAccount();
		List<Transaction> transactions = transactionRepository.findTransactions(account);

		assertEquals("Expected 2 transactions", 2, transactions.size());
	}

	private void testGetTransactions() throws Exception {
		MvcResult result = mockMvc.perform(get("/home/transaction")).andExpect(view().name("transaction/transaction"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content transaction:" + content);

		int foundConnection = content.indexOf("test2@mail.com");
		assertNotEquals("Cannot find connection", foundConnection, -1);

		int foundTransaction1 = content.indexOf("value=\"25.00\"");
		assertNotEquals("Cannot find transaction", foundTransaction1, -1);
		int foundTransaction2 = content.indexOf("value=\"45.00\"");
		assertNotEquals("Cannot find transaction", foundTransaction2, -1);
	}
}
