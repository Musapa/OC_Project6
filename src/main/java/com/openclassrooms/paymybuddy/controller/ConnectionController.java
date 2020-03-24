package com.openclassrooms.paymybuddy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.domain.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Controller
public class ConnectionController {

	private static final Logger log = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "home/connection" }, method = RequestMethod.GET)

	public ModelAndView getConnection() {
		ModelAndView model = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = userRepository.findByEmail(authentication.getName());
		List<Connection> connections = connectionRepository.findConnection(user.getId());

		log.info("Get Connection: " + authentication.getName() +" Number of connections "+connections.size());

		model.addObject("listConnections", connections);
		model.setViewName("connection/connection");
		
		System.out.println("List of connections:" + connections);
		
		return model;
	}
}
