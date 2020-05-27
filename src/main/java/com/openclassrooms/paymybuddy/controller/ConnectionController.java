package com.openclassrooms.paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.dto.UserSelectDto;
import com.openclassrooms.paymybuddy.service.ConnectionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class ConnectionController {

	private static final Logger log = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "home/connection" }, method = RequestMethod.GET)
	public ModelAndView getConnection() {
		ModelAndView model = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = userService.findUserByEmail(authentication.getName());
		List<User> users = connectionService.findUnconnectedUsers(currentUser);
		List<UserSelectDto> selectedUsers = new ArrayList<>();

		for (User user : users) {
			selectedUsers.add(new UserSelectDto(user, false));
		}

		log.info("Get Connection: " + authentication.getName() + " Number of connections " + users.size());

		model.addObject("users", selectedUsers);
		model.setViewName("connection/connection");

		return model;
	}	
	
	@RequestMapping(value = { "home/connection" }, method = RequestMethod.POST)
	public String addConnection(@Valid @ModelAttribute ConnectionDto form, Model model, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("There is a error in addConnection.");
			return "redirect:/error";
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User thisUser = userService.findUserByEmail(authentication.getName());
		if (form.getUsers() != null) {
			for (UserSelectDto user : form.getUsers()) {
				if (user.getSelected()) {
					Connection connection = new Connection(user,thisUser.getAccount());
					thisUser.getAccount().getConnections().add(connection);
					userService.saveUser(thisUser);
					connectionService.saveConnection(connection);
				}
			}
			return "redirect:/home/transaction/";
		} else {
			return "redirect:/home/connection/";
		}
	}

}
