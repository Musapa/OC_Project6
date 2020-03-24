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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.paymybuddy.domain.Connection;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;

@Controller
public class ConnectionController {
	
	private static final Logger log = LoggerFactory.getLogger(ConnectionController.class);
	
	@Autowired
	private ConnectionRepository connectionRepository;
	
	@RequestMapping(value = { "home/connection" }, method = RequestMethod.GET)
	public ModelAndView getConnection() {
		ModelAndView model = new ModelAndView();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Get Connection: " + authentication.getName());
				
		model.setViewName("connection/connection");
		return model;
	}
	
    @RequestMapping("home/connection")
    public List<Connection> getFiltered(@RequestParam Long id) {
        return connectionRepository.findConnection(id);
    }
	
}
